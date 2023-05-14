package ru.cookedapp.cooked.ui.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val lightColorScheme = lightColorScheme(
    primary = MaterialColors.primary40,
    onPrimary = StaticColors.white,
    primaryContainer = MaterialColors.primary90,
    onPrimaryContainer = MaterialColors.primary10,
    secondary = MaterialColors.secondary40,
    onSecondary = StaticColors.white,
    secondaryContainer = MaterialColors.secondary90,
    onSecondaryContainer = MaterialColors.secondary10,
    tertiary = MaterialColors.tertiary40,
    onTertiary = StaticColors.white,
    tertiaryContainer = MaterialColors.tertiary90,
    onTertiaryContainer = MaterialColors.tertiary10,
    background = MaterialColors.neutral99,
    onBackground = MaterialColors.neutral10,
    surface = MaterialColors.neutral99,
    onSurface = MaterialColors.neutral10,
    surfaceVariant = MaterialColors.neutralVariant90,
    onSurfaceVariant = MaterialColors.neutralVariant30,
    error = MaterialColors.error40,
    onError = StaticColors.white,
    errorContainer = MaterialColors.error90,
    onErrorContainer = MaterialColors.error10,
    outline = MaterialColors.neutralVariant50,
)

private val darkColorScheme = darkColorScheme(
    primary = MaterialColors.primary80,
    onPrimary = MaterialColors.primary20,
    primaryContainer = MaterialColors.primary30,
    onPrimaryContainer = MaterialColors.primary90,
    secondary = MaterialColors.secondary80,
    onSecondary = MaterialColors.secondary20,
    secondaryContainer = MaterialColors.secondary30,
    onSecondaryContainer = MaterialColors.secondary90,
    tertiary = MaterialColors.tertiary40,
    onTertiary = MaterialColors.tertiary20,
    tertiaryContainer = MaterialColors.tertiary30,
    onTertiaryContainer = MaterialColors.tertiary90,
    background = MaterialColors.neutral10,
    onBackground = MaterialColors.neutral90,
    surface = MaterialColors.neutral10,
    onSurface = MaterialColors.neutral90,
    surfaceVariant = MaterialColors.neutralVariant30,
    onSurfaceVariant = MaterialColors.neutralVariant80,
    error = MaterialColors.error40,
    onError = MaterialColors.error20,
    errorContainer = MaterialColors.error30,
    onErrorContainer = MaterialColors.error90,
    outline = MaterialColors.neutralVariant60,
)

@Composable
fun CookedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isDynamicThemeEnabled: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        isDynamicThemeEnabled && isDynamicThemingSupported() -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun isDynamicThemingSupported() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
