package ru.cookedapp.common.extensions

import java.text.NumberFormat
import java.util.Locale

fun Double.format(
    decimalPartSize: Int,
    ignoreZeros: Boolean = true,
    groupingEnabled: Boolean = true,
): String = NumberFormat.getInstance(Locale("ru", "RU")).apply {
    maximumFractionDigits = decimalPartSize
    minimumFractionDigits = if (ignoreZeros && this@format == this@format.toInt().toDouble()) 0 else decimalPartSize
    isGroupingUsed = groupingEnabled
}.format(this)
