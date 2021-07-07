package ru.cookedapp.cooked.presentation.adapter

import android.content.Context
import androidx.annotation.LayoutRes
import ru.cookedapp.cooked.data.local.db.entity.RecipeCategory

class RecipeCategoryAutocompleteAdapter(
    context: Context,
    @LayoutRes res: Int,
    items: List<RecipeCategory>
) : BaseAutocompleteAdapter<RecipeCategory>(context, res, items) {
    override fun getItemName(item: Any?): String = (item as RecipeCategory).name
}
