package ru.cookedapp.cooked.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import ru.cookedapp.cooked.data.db.AppDatabase
import javax.inject.Singleton

@Module
class RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Application): AppDatabase =
        AppDatabase.getInstance(context)
}
