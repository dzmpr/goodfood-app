package ru.cookedapp.cooked.ui.recipeList

import ru.cookedapp.cooked.data.db.entity.Recipe
import ru.cookedapp.cooked.data.gateway.PhotoGateway
import ru.cookedapp.cooked.ui.recipeList.data.RecipeListItem
import ru.cookedapp.cooked.ui.recipeList.data.RecipeListViewType

class RecipeListRowFactory {

    fun createRecipeItem(recipe: Recipe, photoGateway: PhotoGateway) = RecipeListItem(
        id = recipe.id!!.toLong(),
        type = RecipeListViewType.RECIPE,
        recipeName = recipe.name,
        recipeCategoryName = recipe.category.name,
        inFavorites = recipe.inFavorites,
        thumbnailUri = recipe.photoFilename?.let {
            photoGateway.getUriForPhoto(it)
        },
    )
}
