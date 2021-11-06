package ru.cookedapp.cooked.ui.recipeList.data

import android.net.Uri
import ru.cookedapp.cooked.utils.listBase.data.Item
import ru.cookedapp.cooked.utils.listBase.data.ItemPayload

data class RecipeListItem(
    override val id: Long,
    override val type: RecipeListViewType,
    val recipeName: String,
    val recipeCategoryName: String,
    val inFavorites: Boolean,
    val thumbnailUri: Uri?,
) : Item<RecipeListViewType> {

    override fun calculatePayload(item: Item<RecipeListViewType>): ItemPayload? {
        item as RecipeListItem
        return if (inFavorites != item.inFavorites) {
            RecipeFavoriteStateChanged(item.inFavorites)
        } else {
            super.calculatePayload(item)
        }
    }
}
