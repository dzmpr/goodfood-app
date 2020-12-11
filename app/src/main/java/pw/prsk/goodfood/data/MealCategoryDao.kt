package pw.prsk.goodfood.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MealCategoryDao: BaseDao<MealCategory> {
    @Query("SELECT * FROM meal_categories")
    fun getAll(): List<MealCategory>

    @Query("SELECT * FROM meal_categories WHERE id = :id")
    fun getById(id: Int): MealCategory
}