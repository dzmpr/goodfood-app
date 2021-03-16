package pw.prsk.goodfood.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import pw.prsk.goodfood.data.local.db.AppDatabase
import javax.inject.Singleton

@Module
class RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Application): AppDatabase =
        AppDatabase.getInstance(context)
}