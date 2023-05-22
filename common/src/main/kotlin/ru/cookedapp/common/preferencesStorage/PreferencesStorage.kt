package ru.cookedapp.common.preferencesStorage

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.core.content.edit
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow

class PreferencesStorage(
    context: Context,
    preferencesName: String,
) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        preferencesName,
        Context.MODE_PRIVATE,
    )

    inline fun <reified T> getValue(key: String, defaultValue: T): T = when (defaultValue) {
        is Int -> sharedPreferences.getInt(key, defaultValue) as T
        is Float -> sharedPreferences.getFloat(key, defaultValue) as T
        is Boolean -> sharedPreferences.getBoolean(key, defaultValue) as T
        is String -> sharedPreferences.getString(key, defaultValue) as T
        is Long -> sharedPreferences.getLong(key, defaultValue) as T
        else -> error("Can't get this type from shared preferences.")
    }

    inline fun <reified T> setValue(key: String, value: T) {
        sharedPreferences.edit(commit = true) {
            when (value) {
                is Int -> this.putInt(key, value as Int)
                is Float -> this.putFloat(key, value as Float)
                is Boolean -> this.putBoolean(key, value as Boolean)
                is String -> this.putString(key, value as String)
                is Long -> this.putLong(key, value as Long)
                else -> error("Can't save this type to shared preferences.")
            }
        }
    }

    fun <T : Any> getValueFlow(key: String, storedValueProvider: () -> T) = callbackFlow {
        trySend(storedValueProvider())

        val listener = OnSharedPreferenceChangeListener { _, changedKey ->
            if (changedKey == key) {
                trySend(storedValueProvider())
            }
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        awaitClose {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }.buffer(UNLIMITED)

    inline fun <reified T: Enum<T>> getEnumOrNull(key: String): T? {
        val value = sharedPreferences.getString(key, null) ?: return null
        return enumValueOf<T>(value)
    }

    inline fun <reified T: Enum<T>> getEnumOrDefault(key: String, defaultValue: T): T {
        val value = sharedPreferences.getString(key, null) ?: return defaultValue
        return enumValueOf(value)
    }

    fun setValue(key: String, value: Enum<*>) = setValue(key, value.name)

    fun getLongOrNull(key: String): Long? = getValueIfExists(key) {
        getValue(key, defaultValue = 0L)
    }

    fun getStringOrNull(key: String): String? = getValueIfExists(key) {
        getValue(key, defaultValue = "")
    }

    fun getFloatOrNull(key: String): Float? = getValueIfExists(key) {
        getValue(key, defaultValue = 0f)
    }

    fun getIntOrNull(key: String): Int? = getValueIfExists(key) {
        getValue(key, defaultValue = 0)
    }

    fun getBooleanOrNull(key: String): Boolean? = getValueIfExists(key) {
        getValue(key, defaultValue = false)
    }

    private inline fun <T: Any> getValueIfExists(
        key: String,
        valueProvider: () -> T,
    ): T? = if (sharedPreferences.contains(key)) valueProvider.invoke() else null
}
