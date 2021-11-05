package ru.cookedapp.cooked.utils.listBase

import android.view.ViewGroup
import ru.cookedapp.cooked.utils.listBase.data.ItemViewType

fun interface ViewHolderFactory<ViewType : ItemViewType> {

    fun create(parent: ViewGroup): BaseViewHolder<ViewType>
}
