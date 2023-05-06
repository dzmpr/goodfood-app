package ru.cookedapp.cooked.ui.settings.data

import ru.cookedapp.storage.appSettings.AppTheme

internal data class SettingsScreenState(
    val appTheme: AppTheme,
    val isDynamicThemeEnabled: Boolean,
)
