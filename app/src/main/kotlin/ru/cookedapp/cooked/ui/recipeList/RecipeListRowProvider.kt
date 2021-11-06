package ru.cookedapp.cooked.ui.recipeList

import javax.inject.Inject
import ru.cookedapp.cooked.data.db.entity.Recipe
import ru.cookedapp.cooked.data.gateway.PhotoGateway

class RecipeListRowProvider @Inject constructor(
    private val photoGateway: PhotoGateway,
) {

    private val rowFactory = RecipeListRowFactory()

    fun generateRows(data: List<Recipe>) = data.map { recipe ->
        rowFactory.createRecipeItem(recipe, photoGateway)
    }
}
