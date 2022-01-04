package ru.cookedapp.cooked.data.prefs

import ru.cookedapp.common.preferencesStorage.PreferencesStorage

class RecipePreferencesImpl(private val storage: PreferencesStorage) : RecipePreferences {
    override var recipeNoCategoryId: Long
        get() = storage.getValue(KEY_NO_CATEGORY, 0)
        set(value) = storage.setValue(KEY_NO_CATEGORY, value)

    companion object {
        const val STORAGE_NAME = "preferences_recipe"

        private const val KEY_NO_CATEGORY = "$STORAGE_NAME.no_category"
    }
}
