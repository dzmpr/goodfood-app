package pw.prsk.goodfood.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProductUnitsDao: BaseDao<ProductUnit> {
    @Query("SELECT * FROM product_units")
    fun getAll(): List<ProductUnit>

    @Query("SELECT * FROM product_units WHERE id = :id")
    fun getById(id: Int): ProductUnit
}