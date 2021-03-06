package pw.prsk.goodfood.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import pw.prsk.goodfood.data.FileGateway
import pw.prsk.goodfood.data.PhotoGateway

@Module
class GatewayModule {
    @Provides
    fun providePhotoGateway(applicationContext: Context, fileGateway: FileGateway): PhotoGateway =
        PhotoGateway(applicationContext, fileGateway)

    @Provides
    fun provideFileGateway(applicationContext: Context): FileGateway =
        FileGateway(applicationContext)
}