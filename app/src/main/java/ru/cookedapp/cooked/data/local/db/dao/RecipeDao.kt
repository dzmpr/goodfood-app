package ru.cookedapp.cooked.data.local.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.cookedapp.cooked.data.local.db.entity.Recipe
import java.time.LocalDateTime

@Dao
interface RecipeDao: BaseDao<Recipe> {
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getById(id: Int): Recipe

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getFlowableById(id: Int): Flow<Recipe>

    @Query("DELETE FROM recipes WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT * FROM recipes WHERE in_favorites")
    fun getFavoriteRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE cook_count != 0 ORDER BY cook_count DESC")
    fun getFrequentRecipes(): Flow<List<Recipe>>

    @Query("UPDATE recipes SET in_favorites = :state WHERE id = :id")
    fun changeFavoriteMark(id: Int, state: Boolean)

    @Query("SELECT COUNT(*) FROM recipes")
    fun isDatabaseEmpty(): Flow<Int>

    @Query("SELECT * FROM recipes WHERE in_favorites ORDER BY cook_count DESC LIMIT 5")
    fun getFavoriteRecipesPreview(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE cook_count != 0 ORDER BY cook_count DESC LIMIT 5")
    fun getFrequentRecipesPreview(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes LIMIT 5")
    fun getAllRecipesPreview(): Flow<List<Recipe>>

    @Query("UPDATE recipes SET cook_count = cook_count + 1 WHERE id = :id")
    fun increaseCookCount(id: Int)

    @Query("UPDATE recipes SET last_cooked = :date WHERE id = :id")
    fun updateLastCookDate(id: Int, date: LocalDateTime)

    @Transaction
    fun markAsCooked(id: Int, date: LocalDateTime) {
        increaseCookCount(id)
        updateLastCookDate(id, date)
    }
}
