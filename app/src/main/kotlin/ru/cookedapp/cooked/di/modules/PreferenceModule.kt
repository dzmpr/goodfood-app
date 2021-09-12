package ru.cookedapp.cooked.di.modules

import dagger.Binds
import dagger.Module
import ru.cookedapp.cooked.data.prefs.PreferenceHelper
import ru.cookedapp.cooked.data.prefs.RecipePreferences
import ru.cookedapp.cooked.data.prefs.SettingsPreferences

@Module
abstract class PreferenceModule {
    @Binds
    abstract fun provideRecipePreferences(prefs: RecipePreferences): PreferenceHelper

    @Binds
    abstract fun provideSettingsPreferences(prefs: SettingsPreferences): PreferenceHelper
}
