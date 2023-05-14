package ru.cookedapp.storage.appSettings

import kotlinx.coroutines.flow.Flow

interface AppSettings {

    var appTheme: AppTheme

    var isDynamicThemeEnabled: Boolean

    val dynamicThemeState: Flow<Boolean>
}
