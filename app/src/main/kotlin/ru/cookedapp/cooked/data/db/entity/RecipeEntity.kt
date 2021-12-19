package ru.cookedapp.cooked.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") override val id: Long,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "instructions") var instructions: String? = null,
    @ColumnInfo(name = "photo_filename") var photoFilename: String? = null,
    @ColumnInfo(name = "servings_num") var servingsNum: Int,
    @ColumnInfo(name = "in_favorites") var inFavorites: Boolean = false,
    @ColumnInfo(name = "last_cooked") var lastCooked: LocalDateTime,
    @ColumnInfo(name = "cook_count") var cookCount: Int = 0,
    @ColumnInfo(name = "ingredients_list") var ingredientsList: List<Ingredient>,
    @ColumnInfo(name = "category_id") var categoryId: Long,
) : Identifiable
