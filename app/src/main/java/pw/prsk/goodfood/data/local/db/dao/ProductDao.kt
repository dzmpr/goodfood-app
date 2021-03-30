package pw.prsk.goodfood.data.local.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pw.prsk.goodfood.data.local.db.entity.Product

@Dao
interface ProductDao : BaseDao<Product> {
    @Query("SELECT * FROM products")
    fun getAll(): List<Product>

    @Query("SELECT * FROM products")
    fun getProductsFlow(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getById(id: Int): Product

    @Query("DELETE FROM products WHERE id = :id")
    fun deleteById(id: Int)

    @Query("UPDATE products SET reference_count = reference_count + 1 WHERE id = :id")
    fun increaseUsages(id: Int)

    @Query("UPDATE products SET reference_count = reference_count - 1 WHERE id = :id")
    fun decreaseUsages(id: Int)

    @Query("UPDATE products SET name = :name WHERE id = :id")
    fun renameProduct(id: Int, name: String)
}