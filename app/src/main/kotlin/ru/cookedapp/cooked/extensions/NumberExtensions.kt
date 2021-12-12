package ru.cookedapp.cooked.extensions

fun Float.cutDecimals(toDigits: Int): String {
    return if (this == toInt().toFloat()) "${toInt()}" else String.format("%.${toDigits}f", this)
}
