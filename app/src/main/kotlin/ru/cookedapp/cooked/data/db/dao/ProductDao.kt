package ru.cookedapp.cooked.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.cookedapp.cooked.data.db.entity.ProductEntity

@Dao
interface ProductDao : BaseDao<ProductEntity> {
    @Query("SELECT * FROM products")
    fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM products")
    fun getProductsFlow(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getById(id: Int): ProductEntity

    @Query("DELETE FROM products WHERE id = :id")
    fun deleteById(id: Int)

    @Query("UPDATE products SET reference_count = reference_count + 1 WHERE id = :id")
    fun increaseUsages(id: Int)

    @Query("UPDATE products SET reference_count = reference_count - 1 WHERE id = :id")
    fun decreaseUsages(id: Int)

    @Query("UPDATE products SET name = :name WHERE id = :id")
    fun renameProduct(id: Int, name: String)
}
