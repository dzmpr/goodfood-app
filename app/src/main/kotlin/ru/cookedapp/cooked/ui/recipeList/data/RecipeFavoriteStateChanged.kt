package ru.cookedapp.cooked.ui.recipeList.data

import ru.cookedapp.cooked.utils.listBase.data.ItemPayload

data class RecipeFavoriteStateChanged(val inFavorites: Boolean) : ItemPayload
