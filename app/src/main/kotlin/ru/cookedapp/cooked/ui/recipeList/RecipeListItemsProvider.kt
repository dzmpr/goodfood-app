package ru.cookedapp.cooked.ui.recipeList

import javax.inject.Inject
import ru.cookedapp.cooked.data.db.entity.Recipe
import ru.cookedapp.cooked.data.gateway.PhotoGateway
import ru.cookedapp.cooked.utils.listBase.data.Items

class RecipeListItemsProvider @Inject constructor(
    private val photoGateway: PhotoGateway,
    private val itemsFactory: RecipeListItemsFactory,
) {

    fun generateRows(data: List<Recipe>): List<Items> = data.map { recipe ->
        itemsFactory.createRecipeItem(recipe, photoGateway)
    }
}
