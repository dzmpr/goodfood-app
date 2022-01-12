package ru.cookedapp.storage.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import ru.cookedapp.storage.PreferencesFactory

@Module
internal interface PreferencesModule {
    companion object {
        @Provides
        @Singleton
        fun providePreferencesFactory(context: Context) = PreferencesFactory(context)

        @Provides
        @Singleton
        fun provideAppSettings(factory: PreferencesFactory) = factory.createAppSettings()
    }
}
