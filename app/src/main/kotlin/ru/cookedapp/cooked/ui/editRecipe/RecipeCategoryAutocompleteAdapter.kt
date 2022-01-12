package ru.cookedapp.cooked.ui.editRecipe

import android.content.Context
import androidx.annotation.LayoutRes
import ru.cookedapp.cooked.ui.base.BaseAutocompleteAdapter
import ru.cookedapp.storage.entity.RecipeCategoryEntity

class RecipeCategoryAutocompleteAdapter(
    context: Context,
    @LayoutRes res: Int,
    items: List<RecipeCategoryEntity>
) : BaseAutocompleteAdapter<RecipeCategoryEntity>(context, res, items) {
    override fun getItemName(item: Any?): String = (item as RecipeCategoryEntity).name
}
