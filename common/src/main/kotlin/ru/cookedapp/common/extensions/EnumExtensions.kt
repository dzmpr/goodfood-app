package ru.cookedapp.common.extensions

inline fun <reified T : Enum<T>> enumValueOfOrNull(value: String?): T? {
    return if (value == null) null else enumValueOf<T>(value)
}
