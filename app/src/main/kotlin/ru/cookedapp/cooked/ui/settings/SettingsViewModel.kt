package ru.cookedapp.cooked.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.cookedapp.cooked.ui.base.ComposeViewModel
import ru.cookedapp.cooked.ui.settings.data.SettingsScreenState
import ru.cookedapp.storage.appSettings.AppSettings
import ru.cookedapp.storage.appSettings.AppTheme
import javax.inject.Inject

internal class SettingsViewModel @Inject constructor(
    private val appSettings: AppSettings,
) : ComposeViewModel<SettingsScreenState>() {

    override val initialState = SettingsScreenState(
        appTheme = appSettings.appTheme,
        isDynamicThemeEnabled = appSettings.isDynamicThemeEnabled,
    )

    private val onSettingsUpdated = Channel<Unit>()

    init {
        onSettingsUpdated.receiveAsFlow().onEach {
            updateState {
                copy(
                    appTheme = appSettings.appTheme,
                    isDynamicThemeEnabled = appSettings.isDynamicThemeEnabled,
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onDynamicThemeStateChanged(isEnabled: Boolean) {
        updateSetting {
            appSettings.isDynamicThemeEnabled = isEnabled
        }
    }

    fun onThemeChanged(selectedTheme: AppTheme) {
        updateSetting {
            appSettings.appTheme = selectedTheme
            val theme = when (selectedTheme) {
                AppTheme.AUTO -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                AppTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                AppTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(theme)
        }
    }

    private fun updateSetting(action: () -> Unit) {
        viewModelScope.launch {
            onSettingsUpdated.send(action())
        }
    }
}
