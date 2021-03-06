package pw.prsk.goodfood.adapters

import android.content.Context
import androidx.annotation.LayoutRes
import pw.prsk.goodfood.data.MealCategory

class MealCategoryAutocompleteAdapter(
    context: Context,
    @LayoutRes res: Int,
    items: List<MealCategory>
) : BaseAutocompleteAdapter<MealCategory>(context, res, items) {
    override fun getItemName(item: Any?): String = (item as MealCategory).name
}