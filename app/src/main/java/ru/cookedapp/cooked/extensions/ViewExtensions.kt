package ru.cookedapp.cooked.extensions

import android.view.View

fun View.setViewVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}
