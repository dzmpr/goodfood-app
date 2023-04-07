package ru.cookedapp.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.cookedapp.storage.entity.ProductUnitEntity

@Dao
interface ProductUnitDao {

    @Insert
    fun insert(entity: ProductUnitEntity): Long

    @Query("SELECT * FROM product_units")
    fun getAll(): List<ProductUnitEntity>

    @Query("SELECT * FROM product_units WHERE id = :id")
    fun getById(id: Long): ProductUnitEntity
}