package ru.cookedapp.cooked.ui.cart

import javax.inject.Inject
import ru.cookedapp.cooked.data.db.entity.CartItem
import ru.cookedapp.cooked.extensions.cutDecimals
import ru.cookedapp.cooked.ui.cart.data.CartProductItem

class CartItemsFactory @Inject constructor() {

    fun createCartItem(cartItem: CartItem) = CartProductItem(
        id = cartItem.id,
        isBought = cartItem.isBought,
        productName = cartItem.product.name,
        amount = cartItem.amount.cutDecimals(2),
        amountUnitName = cartItem.unit.name,
    )
}
