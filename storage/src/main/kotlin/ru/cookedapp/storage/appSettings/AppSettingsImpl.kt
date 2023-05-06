package ru.cookedapp.storage.appSettings

import ru.cookedapp.common.preferencesStorage.PreferencesStorage

class AppSettingsImpl(
    private val storage: PreferencesStorage,
) : AppSettings {
    override var appTheme: AppTheme
        get() = storage.getEnumOrDefault(KEY_APP_THEME, AppTheme.AUTO)
        set(value) = storage.setValue(KEY_APP_THEME, value)

    override var isDynamicThemeEnabled: Boolean
        get() = storage.getValue(KEY_DYNAMIC_THEME, defaultValue = false)
        set(value) = storage.setValue(KEY_DYNAMIC_THEME, value)

    companion object {
        const val STORAGE_NAME = "settings_prefs"

        const val KEY_APP_THEME = "pref_app_theme"
        const val KEY_DYNAMIC_THEME = "is_dynamic_theme_enabled"
    }
}
