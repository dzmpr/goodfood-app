package ru.cookedapp.cooked.ui.recipeList

import javax.inject.Inject
import ru.cookedapp.cooked.data.db.entity.Recipe
import ru.cookedapp.cooked.data.gateway.PhotoGateway
import ru.cookedapp.cooked.ui.recipeList.data.RecipeListItem

class RecipeListItemsFactory @Inject constructor() {

    fun createRecipeItem(
        recipe: Recipe,
        photoGateway: PhotoGateway,
    ) = RecipeListItem(
        id = recipe.id!!.toLong(),
        recipeName = recipe.name,
        recipeCategoryName = recipe.category.name,
        inFavorites = recipe.inFavorites,
        thumbnailUri = recipe.photoFilename?.let {
            photoGateway.getUriForPhoto(it)
        },
    )
}
