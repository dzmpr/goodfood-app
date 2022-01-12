package ru.cookedapp.cooked.ui.editRecipe

import android.content.Context
import androidx.annotation.LayoutRes
import ru.cookedapp.cooked.ui.base.BaseAutocompleteAdapter
import ru.cookedapp.storage.entity.ProductEntity

class ProductAutocompleteAdapter(
    context: Context,
    @LayoutRes resource: Int,
    items: List<ProductEntity>
) : BaseAutocompleteAdapter<ProductEntity>(context, resource, items) {
    override fun getItemName(item: Any?): String = (item as ProductEntity).name
}
