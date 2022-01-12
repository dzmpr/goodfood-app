package ru.cookedapp.storage

import android.content.Context
import ru.cookedapp.common.preferencesStorage.PreferencesStorage
import ru.cookedapp.storage.appSettings.AppSettings
import ru.cookedapp.storage.appSettings.AppSettingsImpl
import ru.cookedapp.storage.recipePreferences.RecipePreferences
import ru.cookedapp.storage.recipePreferences.RecipePreferencesImpl

class PreferencesFactory(private val context: Context) {
    fun createRecipePreferences(): RecipePreferences {
        val storage = createStorage(RecipePreferencesImpl.STORAGE_NAME)
        return RecipePreferencesImpl(storage)
    }

    fun createAppSettings(): AppSettings {
        val storage = createStorage(AppSettingsImpl.STORAGE_NAME)
        return AppSettingsImpl(storage)
    }

    private fun createStorage(name: String): PreferencesStorage = PreferencesStorage(context, name)
}
