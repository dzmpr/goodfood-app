package ru.cookedapp.cooked.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

fun Context.resolveAttribute(resourceId: Int): TypedValue {
    return TypedValue().apply {
        theme.resolveAttribute(resourceId, this, true)
    }
}

fun Context.getColorById(@ColorRes colorResId: Int): Int {
    return ResourcesCompat.getColor(resources, colorResId, theme)
}

fun Context.getDrawableById(@DrawableRes drawableResId: Int): Drawable? {
    return ResourcesCompat.getDrawable(resources, drawableResId, theme)
}
