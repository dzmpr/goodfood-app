package pw.prsk.goodfood.data.dao

import androidx.room.*
import pw.prsk.goodfood.data.Recipe

@Dao
interface RecipeDao: BaseDao<Recipe> {
    @Query("SELECT * FROM meals")
    fun getAll(): List<Recipe>

    @Query("SELECT * FROM meals WHERE id = :id")
    fun getById(id: Int): Recipe

    @Query("DELETE FROM meals WHERE id = :id")
    fun deleteById(id: Int)
}