package pw.prsk.goodfood.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import pw.prsk.goodfood.data.local.db.AppDatabase
import pw.prsk.goodfood.data.local.db.entity.CartItem
import pw.prsk.goodfood.data.local.db.entity.CartItemWithMeta

class CartRepository(private val dbInstance: AppDatabase) {
    suspend fun changeBoughtState(id: Int, state: Boolean) = withContext(Dispatchers.IO) {
        dbInstance.cartDao().changeBoughtState(id, state)
    }

    suspend fun loadCartList() = withContext(Dispatchers.IO) {
        dbInstance.cartDao().getCartList()
            .map { list ->
                list.map {
                    getItemWithMeta(it)
                }
            }
            .flowOn(Dispatchers.IO)
    }

    private fun getItemWithMeta(item: CartItem): CartItemWithMeta {
        val product = dbInstance.productDao().getById(item.productId)
        val unit = dbInstance.productUnitsDao().getById(item.unitId)
        return CartItemWithMeta(
            item.id,
            item.isBought,
            product,
            item.amount,
            unit
        )
    }
}