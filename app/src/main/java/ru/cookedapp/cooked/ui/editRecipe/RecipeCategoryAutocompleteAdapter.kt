package ru.cookedapp.cooked.ui.editRecipe

import android.content.Context
import androidx.annotation.LayoutRes
import ru.cookedapp.cooked.data.db.entity.RecipeCategoryEntity
import ru.cookedapp.cooked.ui.base.BaseAutocompleteAdapter

class RecipeCategoryAutocompleteAdapter(
    context: Context,
    @LayoutRes res: Int,
    items: List<RecipeCategoryEntity>
) : BaseAutocompleteAdapter<RecipeCategoryEntity>(context, res, items) {
    override fun getItemName(item: Any?): String = (item as RecipeCategoryEntity).name
}
