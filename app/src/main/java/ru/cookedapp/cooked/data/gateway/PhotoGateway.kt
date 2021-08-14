package ru.cookedapp.cooked.data.gateway

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class PhotoGateway(private val applicationContext: Context, private val fileGateway: FileGateway) {
    fun createNewPhotoFile(): String {
        while (true) {
            val currentTimestamp = LocalDateTime.now()
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("_ddMMyyyy_HHmmss")
            val filename = "cover" + currentTimestamp.format(formatter)

            if (fileGateway.createNewFile(PHOTOS_INTERNAL_FOLDER, filename)) {
                return filename
            }
        }
    }

    fun getUriForPhoto(filename: String): Uri {
        return fileGateway.getUriForFilename(PHOTOS_INTERNAL_FOLDER, filename)
    }

    fun removePhoto(photo: Uri) {
        fileGateway.removeFile(photo)
    }

    suspend fun copyPhoto(from: Uri, to: Uri) = withContext(Dispatchers.IO) {
        val destinationStream = applicationContext.contentResolver.openOutputStream(to)
        val sourceStream = applicationContext.contentResolver.openInputStream(from)
        sourceStream.use { input ->
            destinationStream.use { output ->
                input!!.copyTo(output!!)
            }
        }
        destinationStream?.close()
        sourceStream?.close()
    }

    suspend fun loadPhoto(photoUri: Uri): Drawable? = withContext(Dispatchers.IO) {
        try {
            val stream = applicationContext.contentResolver.openInputStream(photoUri)
            Drawable.createFromStream(stream, photoUri.toString())
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "Selected recipe photo not found. $e")
            null
        }
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    suspend fun loadScaledPhoto(photoUri: Uri, scaledWidth: Int, scaledHeight: Int) =
        withContext(Dispatchers.IO) {
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            applicationContext.contentResolver.openInputStream(photoUri).use { stream ->
                BitmapFactory.decodeStream(stream, null, options)
            }

            val imageRotationDegrees: Int
            applicationContext.contentResolver.openInputStream(photoUri).use { stream ->
                val exifInterface = ExifInterface(stream!!)
                imageRotationDegrees = exifInterface.rotationDegrees
                Log.d(TAG, "Photo orientation is $imageRotationDegrees.")
            }

            options.apply {
                inSampleSize = calculateInSampleSize(options, scaledWidth, scaledHeight)
                inJustDecodeBounds = false
            }

            applicationContext.contentResolver.openInputStream(photoUri).use { stream ->
                val scaledBitmap = BitmapFactory.decodeStream(stream, null, options)
                return@withContext rotateImage(scaledBitmap!!, imageRotationDegrees.toFloat())
            }
        }

    companion object {
        private const val PHOTOS_INTERNAL_FOLDER = "recipe_photos"

        private const val TAG = "PhotoGateway"
    }
}
