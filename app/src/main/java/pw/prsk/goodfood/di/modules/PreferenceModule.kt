package pw.prsk.goodfood.di.modules

import dagger.Binds
import dagger.Module
import pw.prsk.goodfood.data.local.prefs.PreferenceHelper
import pw.prsk.goodfood.data.local.prefs.RecipePreferences

@Module
abstract class PreferenceModule {
    @Binds
    abstract fun provideRecipePreferences(prefs: RecipePreferences): PreferenceHelper
}