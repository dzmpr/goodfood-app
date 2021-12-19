package ru.cookedapp.cooked.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.cookedapp.cooked.data.db.entity.CartItemEntity

@Dao
interface CartDao {

    @Insert
    fun insert(entity: CartItemEntity): Long

    @Update
    fun update(entity: CartItemEntity)

    @Query("SELECT * FROM cart ORDER BY is_bought ASC")
    fun getCartList(): Flow<List<CartItemEntity>>

    @Query("UPDATE cart SET is_bought = :state WHERE id = :id")
    fun changeBoughtState(id: Long, state: Boolean)

    @Query("SELECT * FROM cart WHERE product_id = :productId AND unit_id = :unitId")
    fun getCartByProductIdAndUnitId(productId: Long, unitId: Long): CartItemEntity?

    @Query("DELETE FROM cart WHERE id = :id")
    fun removeItem(id: Long)

    @Query("DELETE FROM cart WHERE is_bought")
    fun removePurchasedItems()

    @Query("DELETE FROM cart")
    fun clearCart()

    @Query("DELETE FROM cart WHERE product_id = :productId")
    fun removeItemByProductId(productId: Long)
}
