package pw.prsk.goodfood.data

import androidx.room.*

@Dao
interface ProductDao: BaseDao<Product> {
    @Query("SELECT * FROM products")
    fun getAll(): List<Product>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getById(id: Int): Product
}