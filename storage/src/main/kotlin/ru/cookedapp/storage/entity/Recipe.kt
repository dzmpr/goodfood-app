package ru.cookedapp.storage.entity

import java.time.LocalDateTime

data class Recipe(
    val id: Long,
    val name: String,
    val description: String? = null,
    val photoFilename: String? = null,
    val servingsNum: Int,
    val inFavorites: Boolean = false,
    val lastCooked: LocalDateTime,
    val cookCount: Int = 0,
    val ingredientsList: List<IngredientWithMeta>,
    val category: RecipeCategoryEntity,
)
