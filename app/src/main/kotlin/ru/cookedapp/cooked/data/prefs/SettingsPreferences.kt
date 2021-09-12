package ru.cookedapp.cooked.data.prefs

import android.content.Context
import javax.inject.Inject

class SettingsPreferences @Inject constructor(
    context: Context
): PreferenceHelper(PREFERENCES_NAME, context) {
    companion object {
        const val PREFERENCES_NAME = "settings_prefs"

        const val FIELD_APP_THEME = "pref_app_theme"

        const val VAL_THEME_DARK = "${FIELD_APP_THEME}_dark"
        const val VAL_THEME_LIGHT = "${FIELD_APP_THEME}_light"
        const val VAL_THEME_AUTO = "${FIELD_APP_THEME}_auto"
        const val VAL_THEME_SAVER = "${FIELD_APP_THEME}_saver"
    }
}
