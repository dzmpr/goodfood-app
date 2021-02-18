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
    @LayoutRes val resource: Int,
    private val items: List<Product>
): ArrayAdapter<Product>(context, resource, items), Filterable {
    private val suggestions: MutableList<Product> = mutableListOf()

    private val filter = object :Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val query = constraint?.toString()?.toLowerCase(Locale.getDefault())

            val filterResults = FilterResults()
            filterResults.values = if (query.isNullOrEmpty()) {
                items
            } else {
                items.filter { it.name.toLowerCase(Locale.getDefault()).contains(query) }
            }
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            suggestions.clear()
            // Unchecked cast Any to List
            val list = (results?.values as List<*>).filterIsInstance<Product>()
                .takeIf { it.size == (results.values as ArrayList<*>).size }
            list?.also { suggestions.addAll(it) }
            notifyDataSetChanged()
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            return (resultValue as Product).name
        }
    }

    override fun getFilter(): Filter {
        return filter
    }

    override fun getCount(): Int = suggestions.size

    override fun getItem(position: Int): Product  = suggestions[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder?
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resource, null)
            viewHolder = ViewHolder(view.findViewById(R.id.tvItemText))
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.itemText.text = suggestions[position].name
        return view
    }

    class ViewHolder(val itemText: TextView)
}