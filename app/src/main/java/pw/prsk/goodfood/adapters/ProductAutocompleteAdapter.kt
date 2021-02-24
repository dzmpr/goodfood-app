package pw.prsk.goodfood.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.annotation.LayoutRes
import pw.prsk.goodfood.R
import pw.prsk.goodfood.data.Product
import java.util.*

class ProductAutocompleteAdapter(
    context: Context,
    @LayoutRes resource: Int,
    items: List<Product>
) : BaseAutocompleteAdapter<Product>(context, resource, items) {
    override fun getItemName(item: Any?): String = (item as Product).name
}