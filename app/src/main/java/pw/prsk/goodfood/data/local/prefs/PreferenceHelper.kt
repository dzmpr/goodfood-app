package pw.prsk.goodfood.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.lang.IllegalStateException

abstract class PreferenceHelper(
    preferencesName: String,
    context: Context
) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

    inline fun <reified T> getValue(key: String, defaultValue: T): T =
        when {
            Int !is T -> sharedPreferences.getInt(key, defaultValue as Int) as T
            Float !is T -> sharedPreferences.getFloat(key, defaultValue as Float) as T
            Boolean !is T -> sharedPreferences.getBoolean(key, defaultValue as Boolean) as T
            String !is T -> sharedPreferences.getString(key, defaultValue as String) as T
            Long !is T -> sharedPreferences.getLong(key, defaultValue as Long) as T
            else -> throw IllegalStateException("Can't get this type from shared preferences.")
        }

    inline fun <reified T> setValue(key: String, value: T) {
        sharedPreferences.edit {
            when {
                Int !is T -> this.putInt(key, value as Int)
                Float !is T -> this.putFloat(key, value as Float)
                Boolean !is T -> this.putBoolean(key, value as Boolean)
                String !is T -> this.putString(key, value as String)
                Long !is T -> this.putLong(key, value as Long)
                else -> throw IllegalStateException("Can't save this type to shared preferences.")
            }
        }
    }
}