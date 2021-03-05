package pw.prsk.goodfood.data

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PhotoGateway(private val applicationContext: Context) {
    fun createNewPhotoFile() : String {
        while (true) {
            val currentTimestamp = LocalDateTime.now()
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("_ddMMyyyy_HHmmss")
            val fileName = "cover" + currentTimestamp.format(formatter)
            val file = File(applicationContext.getExternalFilesDir("recipe_photos"), fileName)

            if (file.createNewFile()) {
                return fileName
            }
        }
    }

    fun getUriForFilename(folder: String, filename: String): Uri {
        val file = File(applicationContext.getExternalFilesDir(folder), filename)
        return FileProvider.getUriForFile(
            applicationContext,
            "${applicationContext.packageName}.fileprovider", file
        )
    }

    fun removePhoto(photo: Uri) {
        applicationContext.contentResolver.delete(photo, null, null)
    }

    suspend fun copyPhoto(from: Uri, to: Uri) = withContext(Dispatchers.IO) {
        val destinationStream = applicationContext.contentResolver.openOutputStream(to)
        val photoStream = applicationContext.contentResolver.openInputStream(from)
        photoStream.use { input ->
            destinationStream.use { output ->
                input!!.copyTo(output!!)
            }
        }
        destinationStream?.close()
        photoStream?.close()
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
        private const val TAG = "PhotoGateway"
    }
}