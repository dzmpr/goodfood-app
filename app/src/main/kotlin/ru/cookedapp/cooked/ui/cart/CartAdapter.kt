package ru.cookedapp.cooked.ui.cart

import kotlinx.coroutines.CoroutineScope
import ru.cookedapp.cooked.ui.cart.data.CartViewType
import ru.cookedapp.cooked.ui.cart.viewHolders.CartItemHolder
import ru.cookedapp.cooked.utils.listBase.BaseAdapter
import ru.cookedapp.cooked.utils.listBase.ItemEventListener

class CartAdapter(
    scope: CoroutineScope,
    eventListener: ItemEventListener<CartViewType>,
) : BaseAdapter<CartViewType>(scope, eventListener) {

    init {
        registerFactory(CartViewType.ITEM, CartItemHolder.getFactory())
    }
}
