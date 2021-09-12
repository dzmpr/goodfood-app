package ru.cookedapp.cooked.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.cookedapp.cooked.data.gateway.FileGateway
import ru.cookedapp.cooked.data.gateway.PhotoGateway

@Module
class GatewayModule {
    @Provides
    fun providePhotoGateway(applicationContext: Context, fileGateway: FileGateway): PhotoGateway =
        PhotoGateway(applicationContext, fileGateway)

    @Provides
    fun provideFileGateway(applicationContext: Context): FileGateway =
        FileGateway(applicationContext)
}
