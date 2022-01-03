package ru.cookedapp.common.resourceProvider

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes id: Int): String

    fun getString(@StringRes id: Int, vararg formatArguments: Any): String

    fun getStringArray(@ArrayRes id: Int): List<String>
}
