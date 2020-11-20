package pw.prsk.goodfood.data

import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAll(): List<Product>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getById(id: Int): Product

    @Update
    fun update(product: Product)

    @Insert
    fun add(product: Product)

    @Delete
    fun delete(product: Product)
}