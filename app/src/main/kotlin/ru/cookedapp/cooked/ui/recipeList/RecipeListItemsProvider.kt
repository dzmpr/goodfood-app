package ru.cookedapp.cooked.ui.recipeList

import javax.inject.Inject
import ru.cookedapp.common.baseList.data.Items
import ru.cookedapp.cooked.data.db.entity.Recipe
import ru.cookedapp.cooked.data.gateway.PhotoGateway

class RecipeListItemsProvider @Inject constructor(
    private val photoGateway: PhotoGateway,
    private val itemsFactory: RecipeListItemsFactory,
) {

    fun generateRows(data: List<Recipe>): List<Items> = data.map { recipe ->
        itemsFactory.createRecipeItem(recipe, photoGateway)
    }
}
