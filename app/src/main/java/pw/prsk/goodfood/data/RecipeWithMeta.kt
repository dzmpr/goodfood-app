package pw.prsk.goodfood.data

import java.time.LocalDateTime

data class RecipeWithMeta(
    val id: Int? = null,
    var name: String,
    var description: String? = null,
    var photoFilename: String? = null,
    var servingsNum: Int,
    var inFavorites: Boolean = false,
    var last_eaten: LocalDateTime,
    var eat_count: Int = 0,
    var ingredientsList: List<IngredientWithMeta>,
    var category: RecipeCategory?
)