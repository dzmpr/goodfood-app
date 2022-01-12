package ru.cookedapp.cooked.ui.cart

import javax.inject.Inject
import ru.cookedapp.common.baseList.data.Items
import ru.cookedapp.storage.entity.CartItem

class CartItemsProvider @Inject constructor(
    private val itemsFactory: CartItemsFactory,
) {

    fun generateItems(data: List<CartItem>): List<Items> = data.map { item ->
        itemsFactory.createCartItem(item)
    }
}
