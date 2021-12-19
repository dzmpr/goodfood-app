package ru.cookedapp.common.base.list

import android.view.ViewGroup

fun interface ViewHolderFactory {

    fun create(parent: ViewGroup): BaseViewHolder
}
