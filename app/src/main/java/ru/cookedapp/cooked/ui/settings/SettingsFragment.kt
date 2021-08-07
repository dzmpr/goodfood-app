package ru.cookedapp.cooked.ui.settings

import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import androidx.recyclerview.widget.RecyclerView
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.data.prefs.SettingsPreferences


class SettingsFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val screen = preferenceManager.createPreferenceScreen(context)
        preferenceManager.sharedPreferencesName = SettingsPreferences.PREFERENCES_NAME

//        addRecipeCategory(screen)
        addAppCategory(screen)

        preferenceScreen = screen
    }

    private fun addAppCategory(screen: PreferenceScreen) {
        val category = PreferenceCategory(context).apply {
            key = CATEGORY_APP
            title = resources.getString(R.string.label_app_prefs)
            isIconSpaceReserved = false
        }
        screen.addPreference(category)


        val themePreference = ListPreference(context).apply {
            key = SettingsPreferences.FIELD_APP_THEME
            title = resources.getString(R.string.label_app_theme)
            entries = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
                resources.getStringArray(R.array.labels_app_theme_old)
            } else {
                resources.getStringArray(R.array.labels_app_theme_new)
            }
            setDefaultValue(SettingsPreferences.VAL_THEME_AUTO)
            entryValues = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
                arrayOf(
                    SettingsPreferences.VAL_THEME_SAVER,
                    SettingsPreferences.VAL_THEME_LIGHT,
                    SettingsPreferences.VAL_THEME_DARK
                )
            } else {
                arrayOf(
                    SettingsPreferences.VAL_THEME_AUTO,
                    SettingsPreferences.VAL_THEME_LIGHT,
                    SettingsPreferences.VAL_THEME_DARK
                )
            }
            isIconSpaceReserved = false
            summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
            dialogTitle = getString(R.string.label_app_theme)
        }
        themePreference.setOnPreferenceChangeListener { _, newValue ->
            when (newValue) {
                SettingsPreferences.VAL_THEME_DARK -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    true
                }
                SettingsPreferences.VAL_THEME_LIGHT -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    true
                }
                SettingsPreferences.VAL_THEME_AUTO -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    true
                }
                SettingsPreferences.VAL_THEME_SAVER -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                    true
                }
                else -> false
            }
        }
        category.addPreference(themePreference)
    }

    private fun addRecipeCategory(screen: PreferenceScreen) {
        val category = PreferenceCategory(context).apply {
            key = CATEGORY_RECIPE
            title = resources.getString(R.string.label_recipe_prefs)
            isIconSpaceReserved = false
        }
        screen.addPreference(category)

        val defaultServingsNumPreference = EditTextPreference(context).apply {
            key = PREFERENCE_DEFAULT_SERVINGS
            title = resources.getString(R.string.label_default_servings)
            summary = resources.getString(R.string.desc_default_servings)
            isIconSpaceReserved = false
            dialogTitle = resources.getString(R.string.desc_default_servings)
        }
        // Set number input type
        defaultServingsNumPreference.setOnBindEditTextListener { editText ->
            editText.inputType = InputType.TYPE_CLASS_NUMBER
        }
        category.addPreference(defaultServingsNumPreference)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup toolbar
        val toolbar: Toolbar = view.findViewById(R.id.tbToolbar)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fcvContainer).popBackStack()
        }
        // Remove overscroll TODO: test on N
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val list: FrameLayout = view.findViewById(android.R.id.list_container)
            val preferenceRecycler = list.getChildAt(0)
            if (preferenceRecycler is RecyclerView) {
                preferenceRecycler.overScrollMode = View.OVER_SCROLL_NEVER
            }
        }
    }

    companion object {
        const val CATEGORY_RECIPE = "category_recipe"

        const val PREFERENCE_DEFAULT_SERVINGS = "pref_default_servings"

        const val CATEGORY_APP = "category_app"
    }
}
