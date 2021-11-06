package ru.cookedapp.cooked.ui.recipeList.data

import ru.cookedapp.cooked.utils.listBase.data.ItemViewType

enum class RecipeListViewType : ItemViewType {
    RECIPE;

    override val value: Int = ordinal
}
