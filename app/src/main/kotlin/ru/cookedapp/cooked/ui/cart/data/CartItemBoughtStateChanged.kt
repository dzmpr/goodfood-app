package ru.cookedapp.cooked.ui.cart.data

import ru.cookedapp.common.base.list.data.ItemPayload

data class CartItemBoughtStateChanged(
    val isBought: Boolean,
    val productName: String,
) : ItemPayload
