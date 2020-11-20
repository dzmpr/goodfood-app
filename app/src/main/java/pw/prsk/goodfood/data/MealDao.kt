package pw.prsk.goodfood.data

import androidx.room.*

@Dao
interface MealDao {
    @Query("SELECT * FROM meals")
    fun getAll(): List<Meal>

    @Query("SELECT * FROM meals WHERE id = :id")
    fun getById(id: Int): Meal

    @Update
    fun update(meal: Meal)

    @Insert
    fun add(meal: Meal)

    @Delete
    fun delete(meal: Meal)
}