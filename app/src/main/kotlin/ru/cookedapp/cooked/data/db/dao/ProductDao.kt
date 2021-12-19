package ru.cookedapp.cooked.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.cookedapp.cooked.data.db.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert
    fun insert(entity: ProductEntity): Long

    @Delete
    fun delete(entity: ProductEntity)

    @Query("SELECT * FROM products")
    fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM products")
    fun getProductsFlow(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getById(id: Long): ProductEntity

    @Query("DELETE FROM products WHERE id = :id")
    fun deleteById(id: Long)

    @Query("UPDATE products SET reference_count = reference_count + 1 WHERE id = :id")
    fun increaseUsages(id: Long)

    @Query("UPDATE products SET reference_count = reference_count - 1 WHERE id = :id")
    fun decreaseUsages(id: Long)

    @Query("UPDATE products SET name = :name WHERE id = :id")
    fun renameProduct(id: Long, name: String)
}
