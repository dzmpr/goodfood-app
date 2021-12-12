package ru.cookedapp.cooked.utils.listBase

import android.view.ViewGroup

fun interface ViewHolderFactory {

    fun create(parent: ViewGroup): BaseViewHolder
}
