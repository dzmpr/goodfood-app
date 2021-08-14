package ru.cookedapp.cooked.data.prefs

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
        when(defaultValue) {
            is Int -> sharedPreferences.getInt(key, defaultValue as Int) as T
            is Float -> sharedPreferences.getFloat(key, defaultValue as Float) as T
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue as Boolean) as T
            is String -> sharedPreferences.getString(key, defaultValue as String) as T
            is Long -> sharedPreferences.getLong(key, defaultValue as Long) as T
            else -> throw IllegalStateException("Can't get this type from shared preferences.")
        }

    inline fun <reified T> setValue(key: String, value: T) {
        sharedPreferences.edit {
            when(value) {
                is Int -> this.putInt(key, value as Int)
                is Float -> this.putFloat(key, value as Float)
                is Boolean -> this.putBoolean(key, value as Boolean)
                is String -> this.putString(key, value as String)
                is Long -> this.putLong(key, value as Long)
                else -> throw IllegalStateException("Can't save this type to shared preferences.")
            }
        }
    }
}
