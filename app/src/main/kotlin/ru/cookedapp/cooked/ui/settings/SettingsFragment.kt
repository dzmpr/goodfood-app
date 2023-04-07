package ru.cookedapp.cooked.ui.settings

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
import ru.cookedapp.common.extensions.enumValueOfOrNull
import ru.cookedapp.cooked.R
import ru.cookedapp.storage.appSettings.AppSettingsImpl
import ru.cookedapp.storage.appSettings.AppTheme

class SettingsFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val screen = preferenceManager.createPreferenceScreen(requireContext())
        preferenceManager.sharedPreferencesName = AppSettingsImpl.STORAGE_NAME

//        addRecipeCategory(screen)
        addAppCategory(screen)

        preferenceScreen = screen
    }

    private fun addAppCategory(screen: PreferenceScreen) {
        val category = PreferenceCategory(requireContext()).apply {
            key = CATEGORY_APP
            title = resources.getString(R.string.label_app_prefs)
            isIconSpaceReserved = false
        }
        screen.addPreference(category)


        val themePreference = ListPreference(requireContext()).apply {
            key = AppSettingsImpl.KEY_APP_THEME
            title = resources.getString(R.string.label_app_theme)
            entries = resources.getStringArray(R.array.labels_app_theme_new)
            setDefaultValue(AppTheme.AUTO.name)
            entryValues = arrayOf(
                AppTheme.AUTO.name,
                AppTheme.LIGHT.name,
                AppTheme.DARK.name,
            )
            isIconSpaceReserved = false
            summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
            dialogTitle = getString(R.string.label_app_theme)
        }
        themePreference.setOnPreferenceChangeListener { _, newValue ->
            val theme = when (enumValueOfOrNull<AppTheme>(newValue as? String?)) {
                AppTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                AppTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                AppTheme.AUTO -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                else -> error("Unexpected theme constant: $newValue.")
            }
            AppCompatDelegate.setDefaultNightMode(theme)
            true
        }
        category.addPreference(themePreference)
    }

    private fun addRecipeCategory(screen: PreferenceScreen) {
        val category = PreferenceCategory(requireContext()).apply {
            key = CATEGORY_RECIPE
            title = resources.getString(R.string.label_recipe_prefs)
            isIconSpaceReserved = false
        }
        screen.addPreference(category)

        val defaultServingsNumPreference = EditTextPreference(requireContext()).apply {
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
        // Remove overscroll
        val list: FrameLayout = view.findViewById(android.R.id.list_container)
        val preferenceRecycler = list.getChildAt(0)
        if (preferenceRecycler is RecyclerView) {
            preferenceRecycler.overScrollMode = View.OVER_SCROLL_NEVER
        }
    }

    companion object {
        const val CATEGORY_RECIPE = "category_recipe"

        const val PREFERENCE_DEFAULT_SERVINGS = "pref_default_servings"

        const val CATEGORY_APP = "category_app"
    }
}
