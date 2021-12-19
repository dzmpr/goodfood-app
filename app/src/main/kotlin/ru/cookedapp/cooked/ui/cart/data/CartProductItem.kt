package ru.cookedapp.cooked.ui.cart.data

import ru.cookedapp.common.base.list.data.Item
import ru.cookedapp.common.base.list.data.ItemPayload

data class CartProductItem(
    override val id: Long,
    val productName: String,
    val amountUnitName: String,
    val amount: String,
    val isBought: Boolean,
) : Item {

    override fun calculatePayload(item: Item): ItemPayload? {
        item as CartProductItem
        return if (isBought != item.isBought) {
            CartItemBoughtStateChanged(item.isBought, item.productName)
        } else {
            super.calculatePayload(item)
        }
    }
}
