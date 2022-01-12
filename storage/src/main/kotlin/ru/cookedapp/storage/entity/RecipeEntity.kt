package ru.cookedapp.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "instructions")
    val instructions: String?,

    @ColumnInfo(name = "photo_filename")
    val photoFilename: String?,

    @ColumnInfo(name = "servings_num")
    val servingsNum: Int,

    @ColumnInfo(name = "in_favorites")
    val inFavorites: Boolean,

    @ColumnInfo(name = "last_cooked")
    val lastCooked: LocalDateTime,

    @ColumnInfo(name = "cook_count")
    val cookCount: Int,

    @ColumnInfo(name = "ingredients_list")
    val ingredientsList: List<Ingredient>,

    @ColumnInfo(name = "category_id")
    val categoryId: Long,
) : Identifiable {

    constructor(
        name: String,
        instructions: String? = null,
        photoFilename: String? = null,
        servingsNum: Int,
        inFavorites: Boolean = false,
        lastCooked: LocalDateTime,
        cookCount: Int = 0,
        ingredientsList: List<Ingredient>,
        categoryId: Long,
    ) : this(
        id = 0,
        name = name,
        instructions = instructions,
        photoFilename = photoFilename,
        servingsNum = servingsNum,
        inFavorites = inFavorites,
        lastCooked = lastCooked,
        cookCount = cookCount,
        ingredientsList = ingredientsList,
        categoryId = categoryId,
    )
}
