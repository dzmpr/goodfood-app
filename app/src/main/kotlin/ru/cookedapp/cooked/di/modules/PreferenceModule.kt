package ru.cookedapp.cooked.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import ru.cookedapp.cooked.data.prefs.PreferencesFactory

@Module
interface PreferenceModule {
    companion object {
        @Provides
        @Singleton
        fun providePreferencesFactory(context: Context) = PreferencesFactory(context)

        @Provides
        @Singleton
        fun provideAppSettings(factory: PreferencesFactory) = factory.createAppSettings()

        @Provides
        @Singleton
        fun provideRecipePreferences(factory: PreferencesFactory) = factory.createRecipePreferences()
    }
}
