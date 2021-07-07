package ru.cookedapp.cooked.di.modules

import dagger.Binds
import dagger.Module
import ru.cookedapp.cooked.data.local.prefs.PreferenceHelper
import ru.cookedapp.cooked.data.local.prefs.RecipePreferences
import ru.cookedapp.cooked.data.local.prefs.SettingsPreferences

@Module
abstract class PreferenceModule {
    @Binds
    abstract fun provideRecipePreferences(prefs: RecipePreferences): PreferenceHelper

    @Binds
    abstract fun provideSettingsPreferences(prefs: SettingsPreferences): PreferenceHelper
}
