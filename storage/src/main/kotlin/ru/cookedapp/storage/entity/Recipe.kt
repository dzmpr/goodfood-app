package ru.cookedapp.storage.entity

import java.time.LocalDateTime

data class Recipe(
    val id: Long,
    var name: String,
    var description: String? = null,
    var photoFilename: String? = null,
    var servingsNum: Int,
    var inFavorites: Boolean = false,
    var lastCooked: LocalDateTime,
    var cookCount: Int = 0,
    var ingredientsList: List<IngredientWithMeta>,
    var category: RecipeCategoryEntity,
)
