package ru.cookedapp.cooked.ui

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject
import ru.cookedapp.cooked.data.prefs.AppSettings
import ru.cookedapp.cooked.data.prefs.AppTheme
import ru.cookedapp.cooked.di.components.AppComponent
import ru.cookedapp.cooked.di.components.DaggerAppComponent

class CookedApp : Application() {

    @Inject
    lateinit var appSettings: AppSettings

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
        val theme = when (appSettings.appTheme) {
            AppTheme.AUTO -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            AppTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            AppTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(theme)
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
