package ru.cookedapp.cooked.ui.manageItems.data

import ru.cookedapp.cooked.utils.listBase.data.ItemViewType

enum class ManageItemsViewType : ItemViewType {
    ITEM;

    override val value: Int = ordinal
}
