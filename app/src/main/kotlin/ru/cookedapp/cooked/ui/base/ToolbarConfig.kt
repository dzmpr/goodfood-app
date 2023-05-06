package ru.cookedapp.cooked.ui.base

import androidx.annotation.DrawableRes

data class ToolbarConfig(
    val title: String?,
    val subtitle: String?,
    val backButtonEnabled: Boolean = true,
    @DrawableRes val backButtonResId: Int? = null,
)
