package ru.cookedapp.cooked.ui.editRecipe

import android.content.Context
import androidx.annotation.LayoutRes
import ru.cookedapp.cooked.data.db.entity.RecipeCategory
import ru.cookedapp.cooked.ui.base.BaseAutocompleteAdapter

class RecipeCategoryAutocompleteAdapter(
    context: Context,
    @LayoutRes res: Int,
    items: List<RecipeCategory>
) : BaseAutocompleteAdapter<RecipeCategory>(context, res, items) {
    override fun getItemName(item: Any?): String = (item as RecipeCategory).name
}
