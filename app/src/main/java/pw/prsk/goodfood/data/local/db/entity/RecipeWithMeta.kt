package pw.prsk.goodfood.data.local.db.entity

import java.time.LocalDateTime

data class RecipeWithMeta(
    val id: Int? = null,
    var name: String,
    var description: String? = null,
    var photoFilename: String? = null,
    var servingsNum: Int,
    var inFavorites: Boolean = false,
    var lastCooked: LocalDateTime,
    var cookCount: Int = 0,
    var ingredientsList: List<IngredientWithMeta>,
    var category: RecipeCategory
)