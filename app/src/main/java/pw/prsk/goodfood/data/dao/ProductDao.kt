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
}