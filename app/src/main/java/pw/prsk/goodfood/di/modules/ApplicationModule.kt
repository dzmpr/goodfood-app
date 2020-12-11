package pw.prsk.goodfood.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: Application) {
    @Provides
    fun provideApplication(): Application = application
}