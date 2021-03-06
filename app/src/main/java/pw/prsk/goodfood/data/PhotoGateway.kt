package pw.prsk.goodfood.data

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PhotoGateway(private val applicationContext: Context, private val fileGateway: FileGateway) {
    fun createNewPhotoFile() : String {
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

    companion object {
        private const val PHOTOS_INTERNAL_FOLDER = "recipe_photos"

        private const val TAG = "PhotoGateway"
    }
}