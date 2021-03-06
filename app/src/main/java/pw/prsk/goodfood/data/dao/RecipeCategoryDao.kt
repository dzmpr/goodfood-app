package pw.prsk.goodfood.data.dao

import androidx.room.Dao
import androidx.room.Query
import pw.prsk.goodfood.data.RecipeCategory

@Dao
interface RecipeCategoryDao: BaseDao<RecipeCategory> {
    @Query("SELECT * FROM meal_categories")
    fun getAll(): List<RecipeCategory>

    @Query("SELECT * FROM meal_categories WHERE id = :id")
    fun getById(id: Int): RecipeCategory

    @Query("UPDATE meal_categories SET reference_count = reference_count + 1 WHERE id = :id")
    fun increaseUsages(id: Int)

    @Query("UPDATE meal_categories SET reference_count = reference_count - 1 WHERE id = :id")
    fun decreaseUsages(id: Int)
}