package pw.prsk.goodfood.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import pw.prsk.goodfood.R
import pw.prsk.goodfood.data.ProductUnit

class ProductUnitAdapter(
    context: Context,
    @LayoutRes val resource: Int,
    private val items: List<ProductUnit>
): ArrayAdapter<ProductUnit>(context, resource, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(resource, null)
        }
        val tvItem: TextView = view!!.findViewById(R.id.tvItemText)
        tvItem.text = items[position].name
        return view
    }
}