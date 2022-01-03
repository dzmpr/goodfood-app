package ru.cookedapp.cooked.ui

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject
import ru.cookedapp.cooked.data.prefs.SettingsPreferences
import ru.cookedapp.cooked.di.components.AppComponent
import ru.cookedapp.cooked.di.components.DaggerAppComponent

class CookedApp : Application() {

    @Inject lateinit var settingsPrefs: SettingsPreferences

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().run {
            bindContext(this@CookedApp)
            bindApplication(this@CookedApp)
            build()
        }.also {
            it.inject(this)
        }

        applyTheme()
    }

    private fun applyTheme() {
        val savedTheme = settingsPrefs.getValue(
            SettingsPreferences.FIELD_APP_THEME,
            SettingsPreferences.VAL_THEME_AUTO,
        )
        val theme = when (savedTheme) {
            SettingsPreferences.VAL_THEME_AUTO -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            SettingsPreferences.VAL_THEME_DARK -> AppCompatDelegate.MODE_NIGHT_YES
            SettingsPreferences.VAL_THEME_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            else -> error("Unexpected theme constant: $savedTheme.")
        }
        AppCompatDelegate.setDefaultNightMode(theme)
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
