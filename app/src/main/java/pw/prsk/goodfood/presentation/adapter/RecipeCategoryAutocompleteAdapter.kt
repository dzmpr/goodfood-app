package pw.prsk.goodfood.presentation.adapter

import android.content.Context
import androidx.annotation.LayoutRes
import pw.prsk.goodfood.data.local.db.entity.RecipeCategory

class RecipeCategoryAutocompleteAdapter(
    context: Context,
    @LayoutRes res: Int,
    items: List<RecipeCategory>
) : BaseAutocompleteAdapter<RecipeCategory>(context, res, items) {
    override fun getItemName(item: Any?): String = (item as RecipeCategory).name
}