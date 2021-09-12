package ru.cookedapp.cooked.ui.editRecipe

import android.content.Context
import androidx.annotation.LayoutRes
import ru.cookedapp.cooked.data.db.entity.ProductEntity
import ru.cookedapp.cooked.ui.base.BaseAutocompleteAdapter

class ProductAutocompleteAdapter(
    context: Context,
    @LayoutRes resource: Int,
    items: List<ProductEntity>
) : BaseAutocompleteAdapter<ProductEntity>(context, resource, items) {
    override fun getItemName(item: Any?): String = (item as ProductEntity).name
}
