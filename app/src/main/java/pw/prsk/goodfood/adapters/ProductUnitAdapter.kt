package pw.prsk.goodfood.adapters

import android.content.Context
import androidx.annotation.LayoutRes
import pw.prsk.goodfood.data.ProductUnit

class ProductUnitAdapter(
    context: Context,
    @LayoutRes resource: Int,
    items: List<ProductUnit>
) : BaseDropdownAdapter<ProductUnit>(context, resource, items) {
    override fun getItemName(item: ProductUnit): String = item.name
}