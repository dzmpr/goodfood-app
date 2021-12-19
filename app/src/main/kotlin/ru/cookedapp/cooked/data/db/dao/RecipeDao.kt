package ru.cookedapp.cooked.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow
import ru.cookedapp.cooked.data.db.entity.RecipeEntity

@Dao
interface RecipeDao {

    @Insert
    fun insert(entity: RecipeEntity): Long

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getById(id: Long): RecipeEntity

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getFlowableById(id: Long): Flow<RecipeEntity>

    @Query("DELETE FROM recipes WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT * FROM recipes WHERE in_favorites")
    fun getFavoriteRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE cook_count != 0 ORDER BY cook_count DESC")
    fun getFrequentRecipes(): Flow<List<RecipeEntity>>

    @Query("UPDATE recipes SET in_favorites = :state WHERE id = :id")
    fun changeFavoriteMark(id: Long, state: Boolean)

    @Query("SELECT COUNT(*) FROM recipes")
    fun isDatabaseEmpty(): Flow<Int>

    @Query("SELECT * FROM recipes WHERE in_favorites ORDER BY cook_count DESC LIMIT 5")
    fun getFavoriteRecipesPreview(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE cook_count != 0 ORDER BY cook_count DESC LIMIT 5")
    fun getFrequentRecipesPreview(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes LIMIT 5")
    fun getAllRecipesPreview(): Flow<List<RecipeEntity>>

    @Query("UPDATE recipes SET cook_count = cook_count + 1 WHERE id = :id")
    fun increaseCookCount(id: Long)

    @Query("UPDATE recipes SET last_cooked = :date WHERE id = :id")
    fun updateLastCookDate(id: Long, date: LocalDateTime)

    @Transaction
    fun markAsCooked(id: Long, date: LocalDateTime) {
        increaseCookCount(id)
        updateLastCookDate(id, date)
    }
}
