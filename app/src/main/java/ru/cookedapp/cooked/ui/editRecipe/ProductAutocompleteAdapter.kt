package ru.cookedapp.cooked.ui.editRecipe

import android.content.Context
import androidx.annotation.LayoutRes
import ru.cookedapp.cooked.data.db.entity.Product
import ru.cookedapp.cooked.ui.base.BaseAutocompleteAdapter

class ProductAutocompleteAdapter(
    context: Context,
    @LayoutRes resource: Int,
    items: List<Product>
) : BaseAutocompleteAdapter<Product>(context, resource, items) {
    override fun getItemName(item: Any?): String = (item as Product).name
}
