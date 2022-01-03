package ru.cookedapp.cooked.ui.recipeList.data

import android.net.Uri
import ru.cookedapp.common.baseList.data.Item
import ru.cookedapp.common.baseList.data.ItemPayload

data class RecipeListItem(
    override val id: Long,
    val recipeName: String,
    val recipeCategoryName: String,
    val inFavorites: Boolean,
    val thumbnailUri: Uri?,
) : Item {

    override fun calculatePayload(item: Item): ItemPayload? {
        item as RecipeListItem
        return if (inFavorites != item.inFavorites) {
            RecipeFavoriteStateChanged(item.inFavorites)
        } else {
            super.calculatePayload(item)
        }
    }
}
