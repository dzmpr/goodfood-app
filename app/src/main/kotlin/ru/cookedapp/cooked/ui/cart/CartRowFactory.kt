package ru.cookedapp.cooked.ui.cart

import ru.cookedapp.cooked.data.db.entity.CartItem
import ru.cookedapp.cooked.extensions.cutDecimals
import ru.cookedapp.cooked.ui.cart.data.CartItemModel
import ru.cookedapp.cooked.ui.cart.data.CartViewType

class CartRowFactory {

    fun createCartItem(cartItem: CartItem) = CartItemModel(
        id = cartItem.id!!.toLong(),
        type = CartViewType.ITEM,
        isBought = cartItem.isBought,
        productName = cartItem.product.name,
        amount = cartItem.amount.cutDecimals(2),
        amountUnitName = cartItem.unit.name,
    )
}
