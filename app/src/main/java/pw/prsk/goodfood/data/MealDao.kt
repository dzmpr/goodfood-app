package pw.prsk.goodfood.data

import androidx.room.*

@Dao
interface MealDao: BaseDao<Meal> {
    @Query("SELECT * FROM meals")
    fun getAll(): List<Meal>

    @Query("SELECT * FROM meals WHERE id = :id")
    fun getById(id: Int): Meal
}