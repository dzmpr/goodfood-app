package ru.cookedapp.cooked.ui.recipeList.data

import ru.cookedapp.common.base.list.data.ItemPayload

data class RecipeFavoriteStateChanged(val inFavorites: Boolean) : ItemPayload
