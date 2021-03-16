package pw.prsk.goodfood.presentation.adapter

import android.content.Context
import androidx.annotation.LayoutRes
import pw.prsk.goodfood.data.local.db.entity.Product

class ProductAutocompleteAdapter(
    context: Context,
    @LayoutRes resource: Int,
    items: List<Product>
) : BaseAutocompleteAdapter<Product>(context, resource, items) {
    override fun getItemName(item: Any?): String = (item as Product).name
}