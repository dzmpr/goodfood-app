package ru.cookedapp.cooked.ui.cart

import javax.inject.Inject
import ru.cookedapp.cooked.data.db.entity.CartItem
import ru.cookedapp.cooked.utils.listBase.data.Items

class CartItemsProvider @Inject constructor(
    private val itemsFactory: CartItemsFactory,
) {

    fun generateItems(data: List<CartItem>): List<Items> = data.map { item ->
        itemsFactory.createCartItem(item)
    }
}
