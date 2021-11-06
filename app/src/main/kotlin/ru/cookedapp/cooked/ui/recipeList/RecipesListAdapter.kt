package ru.cookedapp.cooked.ui.recipeList

import kotlinx.coroutines.CoroutineScope
import ru.cookedapp.cooked.ui.recipeList.data.RecipeListViewType
import ru.cookedapp.cooked.ui.recipeList.viewHolders.RecipeViewHolder
import ru.cookedapp.cooked.utils.listBase.BaseAdapter
import ru.cookedapp.cooked.utils.listBase.ItemEventListener

class RecipesListAdapter(
    adapterScope: CoroutineScope,
    eventListener: ItemEventListener<RecipeListViewType>,
) : BaseAdapter<RecipeListViewType>(adapterScope, eventListener) {

    init {
        registerFactory(RecipeListViewType.RECIPE, RecipeViewHolder.getFactory())
    }
}
