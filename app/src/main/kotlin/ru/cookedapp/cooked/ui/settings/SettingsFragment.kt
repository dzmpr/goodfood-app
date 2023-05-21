package ru.cookedapp.cooked.ui.settings

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.ui.CookedApp
import ru.cookedapp.cooked.ui.base.ComposeFragment
import ru.cookedapp.cooked.ui.components.ScreenScaffold
import ru.cookedapp.cooked.ui.settings.data.SettingsScreenState
import ru.cookedapp.cooked.ui.theme.CookedTheme
import ru.cookedapp.cooked.ui.theme.isDynamicThemingSupported
import ru.cookedapp.storage.appSettings.AppTheme

internal class SettingsFragment : ComposeFragment() {

    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CookedApp.appComponent.inject(this)
    }

    @Composable
    override fun Content() {
        ScreenScaffold(
            screenTitle = stringResource(R.string.settings_title),
            onBackPressed = {
                navController.popBackStack()
            },
        ) {
            val state by viewModel.state.collectAsStateWithLifecycle()
            SettingsScreen(state)
        }
    }

    @Composable
    private fun SettingsScreen(
        state: SettingsScreenState,
    ) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(vertical = 8.dp),
        ) {
            Text(
                text = stringResource(R.string.setting_section_recipe_prefs),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            navController.navigate(R.id.actionNavigateToCategories)
                        },
                    )
                    .padding(horizontal = 16.dp, vertical = 20.dp),
            ) {
                Text(text = stringResource(R.string.setting_manage_categories))
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = null)
            }
            Text(
                text = stringResource(R.string.setting_section_app_prefs),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            Text(
                text = stringResource(R.string.settings_theme),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            )
            AppTheme.values().forEach { theme ->
                ThemeRow(theme = theme, selectedTheme = state.appTheme) {
                    viewModel.onThemeChanged(theme)
                }
            }
            if (isDynamicThemingSupported()) {
                Text(
                    text = stringResource(R.string.settings_dynamic_theme),
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                )
                SwitchRow(
                    text = stringResource(R.string.settings_dynamic_theme_switch),
                    isChecked = state.isDynamicThemeEnabled,
                )
            }
        }
    }

    @Composable
    private fun ThemeRow(
        theme: AppTheme,
        selectedTheme: AppTheme,
        onRowClicked: () -> Unit,
    ) {
        val nameRes = when (theme) {
            AppTheme.AUTO -> R.string.settings_theme_follow_system
            AppTheme.LIGHT -> R.string.settings_theme_light_theme
            AppTheme.DARK -> R.string.settings_theme_dark_theme
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(onClick = onRowClicked)
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = stringResource(nameRes),
            )
            Spacer(modifier = Modifier.weight(1f))
            RadioButton(
                selected = theme == selectedTheme,
                onClick = onRowClicked,
            )
        }
    }

    @Composable
    private fun SwitchRow(
        text: String,
        isChecked: Boolean,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    viewModel.onDynamicThemeStateChanged(!isChecked)
                }
                .padding(horizontal = 16.dp),
        ) {
            Text(text = text)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isChecked,
                onCheckedChange = {
                    viewModel.onDynamicThemeStateChanged(!isChecked)
                },
            )
        }
    }

    @Preview
    @Composable
    private fun Preview() {
        CookedTheme {
            val state = SettingsScreenState(
                appTheme = AppTheme.DARK,
                isDynamicThemeEnabled = true,
            )
            SettingsScreen(state = state)
        }
    }
}
