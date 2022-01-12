package ru.cookedapp.storage.appSettings

import ru.cookedapp.common.preferencesStorage.PreferencesStorage

class AppSettingsImpl(
    private val storage: PreferencesStorage,
) : AppSettings {
    override var appTheme: AppTheme
        get() = storage.getEnumOrDefault(KEY_APP_THEME, AppTheme.AUTO)
        set(value) = storage.setValue(KEY_APP_THEME, value)

    companion object {
        const val STORAGE_NAME = "settings_prefs"

        const val KEY_APP_THEME = "pref_app_theme"
    }
}
