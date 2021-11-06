package ru.cookedapp.cooked.ui.cart

import ru.cookedapp.cooked.data.db.entity.CartItem
import ru.cookedapp.cooked.ui.cart.data.CartViewType
import ru.cookedapp.cooked.utils.listBase.data.Item

class CartRowProvider {

    private val rowFactory = CartRowFactory()

    fun generateItems(data: List<CartItem>): List<Item<CartViewType>> = data.map { item ->
        rowFactory.createCartItem(item)
    }
}
