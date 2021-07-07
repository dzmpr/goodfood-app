package ru.cookedapp.cooked.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.cookedapp.cooked.data.db.entity.RecipeCategory

@Dao
interface RecipeCategoryDao: BaseDao<RecipeCategory> {
    @Query("SELECT * FROM recipe_categories")
    fun getAll(): List<RecipeCategory>

    @Query("SELECT * FROM recipe_categories")
    fun getCategoriesFlow(): Flow<List<RecipeCategory>>

    @Query("SELECT * FROM recipe_categories WHERE id = :id")
    fun getById(id: Int): RecipeCategory

    @Query("UPDATE recipe_categories SET reference_count = reference_count + 1 WHERE id = :id")
    fun increaseUsages(id: Int)

    @Query("UPDATE recipe_categories SET reference_count = reference_count - 1 WHERE id = :id")
    fun decreaseUsages(id: Int)

    @Query("UPDATE recipe_categories SET name = :categoryName WHERE id = :id")
    fun renameCategory(id: Int, categoryName: String)
}
