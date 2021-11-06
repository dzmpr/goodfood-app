package ru.cookedapp.cooked.ui.cart.data

import ru.cookedapp.cooked.utils.listBase.data.ItemViewType

enum class CartViewType : ItemViewType {
    ITEM;

    override val value: Int = ordinal
}
