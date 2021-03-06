package pw.prsk.goodfood.data.dao

import androidx.room.*
import pw.prsk.goodfood.data.Product

@Dao
interface ProductDao : BaseDao<Product> {
    @Query("SELECT * FROM products")
    fun getAll(): List<Product>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getById(id: Int): Product

    @Query("DELETE FROM products WHERE id = :id")
    fun deleteById(id: Int)

    @Query("UPDATE products SET reference_count = reference_count + 1 WHERE id = :id")
    fun increaseUsages(id: Int)

    @Query("UPDATE products SET reference_count = reference_count - 1 WHERE id = :id")
    fun decreaseUsages(id: Int)
}