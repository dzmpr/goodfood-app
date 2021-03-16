package pw.prsk.goodfood.data.gateway

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

class FileGateway(private val applicationContext: Context) {
    fun createNewFile(internalFolder: String, filename: String): Boolean {
        val file = File(applicationContext.getExternalFilesDir(internalFolder), filename)
        return file.createNewFile()
    }

    fun getUriForFilename(folder: String, filename: String): Uri {
        val file = File(applicationContext.getExternalFilesDir(folder), filename)
        return FileProvider.getUriForFile(
            applicationContext,
            "${applicationContext.packageName}.fileprovider", file
        )
    }

    fun removeFile(file: Uri) {
        applicationContext.contentResolver.delete(file, null, null)
    }
}