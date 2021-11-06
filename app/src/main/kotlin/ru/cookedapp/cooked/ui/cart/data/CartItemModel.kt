package ru.cookedapp.cooked.ui.cart.data

import ru.cookedapp.cooked.utils.listBase.data.Item
import ru.cookedapp.cooked.utils.listBase.data.ItemPayload

data class CartItemModel(
    override val id: Long,
    override val type: CartViewType,
    val productName: String,
    val amountUnitName: String,
    val amount: String,
    val isBought: Boolean,
) : Item<CartViewType> {

    override fun calculatePayload(item: Item<CartViewType>): ItemPayload? {
        item as CartItemModel
        return if (isBought != item.isBought) {
            CartItemBoughtStateChanged(item.isBought, item.productName)
        } else {
            super.calculatePayload(item)
        }
    }
}
