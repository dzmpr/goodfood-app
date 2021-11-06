package ru.cookedapp.cooked.ui.cart.data

import ru.cookedapp.cooked.utils.listBase.data.ItemPayload

data class CartItemBoughtStateChanged(
    val isBought: Boolean,
    val productName: String,
) : ItemPayload
