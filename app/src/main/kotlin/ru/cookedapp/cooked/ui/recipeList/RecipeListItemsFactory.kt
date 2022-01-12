package ru.cookedapp.cooked.ui.recipeList

import javax.inject.Inject
import ru.cookedapp.cooked.data.gateway.PhotoGateway
import ru.cookedapp.cooked.ui.recipeList.data.RecipeListItem
import ru.cookedapp.storage.entity.Recipe

class RecipeListItemsFactory @Inject constructor() {

    fun createRecipeItem(
        recipe: Recipe,
        photoGateway: PhotoGateway,
    ) = RecipeListItem(
        id = recipe.id,
        recipeName = recipe.name,
        recipeCategoryName = recipe.category.name,
        inFavorites = recipe.inFavorites,
        thumbnailUri = recipe.photoFilename?.let {
            photoGateway.getUriForPhoto(it)
        },
    )
}
