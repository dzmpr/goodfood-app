package pw.prsk.goodfood.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pw.prsk.goodfood.data.Recipe

@Dao
interface RecipeDao: BaseDao<Recipe> {
    @Query("SELECT * FROM meals")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM meals WHERE id = :id")
    fun getById(id: Int): Recipe

    @Query("DELETE FROM meals WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT * FROM meals WHERE in_favorites")
    fun getFavoriteRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM meals WHERE eat_count != 0 ORDER BY eat_count DESC")
    fun getFrequentRecipes(): Flow<List<Recipe>>

    @Query("UPDATE meals SET in_favorites = :state WHERE id = :id")
    fun changeFavoriteMark(id: Int, state: Boolean)

    @Query("SELECT COUNT(*) FROM meals")
    fun isDatabaseEmpty(): Flow<Int>
}