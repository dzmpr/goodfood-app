package pw.prsk.goodfood.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import pw.prsk.goodfood.data.PhotoGateway

@Module
class GatewayModule {
    @Provides
    fun providePhotoGateway(applicationContext: Context): PhotoGateway =
        PhotoGateway(applicationContext)
}