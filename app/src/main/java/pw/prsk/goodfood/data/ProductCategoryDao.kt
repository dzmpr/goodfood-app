package pw.prsk.goodfood.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProductCategoryDao: BaseDao<ProductCategory> {
    @Query("SELECT * FROM product_categories")
    fun getAll(): List<ProductCategory>

    @Query("SELECT * FROM product_categories WHERE id = :id")
    fun getById(id: Int): ProductCategory
}