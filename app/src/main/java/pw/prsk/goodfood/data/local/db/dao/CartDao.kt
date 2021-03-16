package pw.prsk.goodfood.data.local.db.dao

import androidx.room.*
import pw.prsk.goodfood.data.local.db.entity.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao: BaseDao<CartItem> {
    @Query("SELECT * FROM cart ORDER BY is_bought ASC")
    fun getCartList(): Flow<List<CartItem>>

    @Query("UPDATE cart SET is_bought = :state WHERE id = :id")
    fun changeBoughtState(id: Int, state: Boolean)

    @Query("SELECT * FROM cart WHERE product_id = :productId AND unit_id = :unitId")
    fun getCartByProductIdAndUnitId(productId: Int, unitId: Int): CartItem?

    @Query("DELETE FROM cart WHERE id = :id")
    fun removeItem(id: Int)

    @Query("DELETE FROM cart WHERE is_bought")
    fun removePurchasedItems()

    @Query("DELETE FROM cart")
    fun clearCart()

    @Query("DELETE FROM cart WHERE product_id = :productId")
    fun removeItemByProductId(productId: Int)
}