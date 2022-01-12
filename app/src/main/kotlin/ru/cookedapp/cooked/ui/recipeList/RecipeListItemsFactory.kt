package ru.cookedapp.cooked.ui.recipeList

import javax.inject.Inject
import ru.cookedapp.common.resourceProvider.ResourceProvider
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.data.gateway.PhotoGateway
import ru.cookedapp.cooked.ui.recipeList.data.RecipeListItem
import ru.cookedapp.storage.entity.Recipe

class RecipeListItemsFactory @Inject constructor(
    private val rp: ResourceProvider,
) {

    fun createRecipeItem(
        recipe: Recipe,
        photoGateway: PhotoGateway,
    ) = RecipeListItem(
        id = recipe.id,
        recipeName = recipe.name,
        recipeCategoryName = recipe.category?.name ?: rp.getString(R.string.label_no_category),
        inFavorites = recipe.inFavorites,
        thumbnailUri = recipe.photoFilename?.let {
            photoGateway.getUriForPhoto(it)
        },
    )
}
