package pw.prsk.goodfood.adapters

import android.content.Context
import androidx.annotation.LayoutRes
import pw.prsk.goodfood.data.RecipeCategory

class RecipeCategoryAutocompleteAdapter(
    context: Context,
    @LayoutRes res: Int,
    items: List<RecipeCategory>
) : BaseAutocompleteAdapter<RecipeCategory>(context, res, items) {
    override fun getItemName(item: Any?): String = (item as RecipeCategory).name
}