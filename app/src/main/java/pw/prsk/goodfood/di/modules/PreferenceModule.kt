package pw.prsk.goodfood.di.modules

import dagger.Binds
import dagger.Module
import pw.prsk.goodfood.data.local.prefs.PreferenceHelper
import pw.prsk.goodfood.data.local.prefs.RecipePreferences
import pw.prsk.goodfood.data.local.prefs.SettingsPreferences

@Module
abstract class PreferenceModule {
    @Binds
    abstract fun provideRecipePreferences(prefs: RecipePreferences): PreferenceHelper

    @Binds
    abstract fun provideSettingsPreferences(prefs: SettingsPreferences): PreferenceHelper
}