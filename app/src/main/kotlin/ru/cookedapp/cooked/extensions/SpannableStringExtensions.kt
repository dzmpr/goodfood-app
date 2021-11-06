package ru.cookedapp.cooked.extensions

import android.text.Spannable
import android.text.SpannableString
import android.text.style.StrikethroughSpan

fun SpannableString.addStrikethroughSpan(
    position: Pair<Int, Int>,
) = setSpan(
    StrikethroughSpan(),
    position.first,
    position.second,
    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
)
