package pw.prsk.goodfood.data

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.util.*

class PhotoGateway(private val applicationContext: Context) {
    fun createNewPhotoUri() : Uri? {
        val fileName = UUID.randomUUID().toString()
        val photo = File(applicationContext.getExternalFilesDir("recipe_photos"), fileName)

        return if (photo.createNewFile()) {
            FileProvider.getUriForFile(applicationContext,
                "${applicationContext.packageName}.fileprovider", photo)
        } else {
            null
        }
    }

    fun removePhoto(photo: Uri) {
        applicationContext.contentResolver.delete(photo, null, null)
    }
}