package pw.prsk.goodfood.di.modules

import android.content.Context
import dagger.Binds
import dagger.Module
import pw.prsk.goodfood.data.local.PreferenceHelper
import pw.prsk.goodfood.data.local.RecipePreferences

@Module
abstract class PreferenceModule {
    @Binds
    abstract fun provideRecipePreferences(prefs: RecipePreferences): PreferenceHelper
}