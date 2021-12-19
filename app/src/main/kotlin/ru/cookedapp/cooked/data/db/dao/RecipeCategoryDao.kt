package ru.cookedapp.cooked.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.cookedapp.cooked.data.db.entity.RecipeCategoryEntity

@Dao
interface RecipeCategoryDao {

    @Insert
    fun insert(entity: RecipeCategoryEntity): Long

    @Delete
    fun delete(entity: RecipeCategoryEntity)

    @Query("SELECT * FROM recipe_categories")
    fun getAll(): List<RecipeCategoryEntity>

    @Query("SELECT * FROM recipe_categories")
    fun getCategoriesFlow(): Flow<List<RecipeCategoryEntity>>

    @Query("SELECT * FROM recipe_categories WHERE id = :id")
    fun getById(id: Long): RecipeCategoryEntity

    @Query("UPDATE recipe_categories SET reference_count = reference_count + 1 WHERE id = :id")
    fun increaseUsages(id: Long)

    @Query("UPDATE recipe_categories SET reference_count = reference_count - 1 WHERE id = :id")
    fun decreaseUsages(id: Long)

    @Query("UPDATE recipe_categories SET name = :categoryName WHERE id = :id")
    fun renameCategory(id: Long, categoryName: String)
}
