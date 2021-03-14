package pw.prsk.goodfood.data.local.prefs

import android.content.Context
import javax.inject.Inject

class RecipePreferences @Inject constructor(
    context: Context
) : PreferenceHelper(PREFERENCES_NAME, context) {
    companion object {
        const val PREFERENCES_NAME = "preferences_recipe"

        const val FIELD_NO_CATEGORY = "$PREFERENCES_NAME.no_category"
    }
}