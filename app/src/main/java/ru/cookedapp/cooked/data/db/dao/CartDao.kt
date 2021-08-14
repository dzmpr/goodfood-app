package ru.cookedapp.cooked.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.cookedapp.cooked.data.db.entity.CartItemEntity

@Dao
interface CartDao: BaseDao<CartItemEntity> {
    @Query("SELECT * FROM cart ORDER BY is_bought ASC")
    fun getCartList(): Flow<List<CartItemEntity>>

    @Query("UPDATE cart SET is_bought = :state WHERE id = :id")
    fun changeBoughtState(id: Int, state: Boolean)

    @Query("SELECT * FROM cart WHERE product_id = :productId AND unit_id = :unitId")
    fun getCartByProductIdAndUnitId(productId: Int, unitId: Int): CartItemEntity?

    @Query("DELETE FROM cart WHERE id = :id")
    fun removeItem(id: Int)

    @Query("DELETE FROM cart WHERE is_bought")
    fun removePurchasedItems()

    @Query("DELETE FROM cart")
    fun clearCart()

    @Query("DELETE FROM cart WHERE product_id = :productId")
    fun removeItemByProductId(productId: Int)
}
