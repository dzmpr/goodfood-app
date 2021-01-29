package pw.prsk.goodfood.data.dao

import androidx.room.*
import pw.prsk.goodfood.data.Meal

@Dao
interface MealDao: BaseDao<Meal> {
    @Query("SELECT * FROM meals")
    fun getAll(): List<Meal>

    @Query("SELECT * FROM meals WHERE id = :id")
    fun getById(id: Int): Meal
}