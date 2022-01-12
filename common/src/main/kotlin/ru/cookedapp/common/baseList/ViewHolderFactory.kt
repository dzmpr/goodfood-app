package ru.cookedapp.common.baseList

import android.view.ViewGroup

fun interface ViewHolderFactory {

    fun create(parent: ViewGroup): BaseViewHolder
}
