package pw.prsk.goodfood.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import pw.prsk.goodfood.R

abstract class BaseDropdownAdapter<T>(
    context: Context,
    @LayoutRes val resource: Int,
    private val items: List<T>
) : ArrayAdapter<T>(context, resource, items) {
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
        viewHolder.itemText.text = getItemName(items[position])
        return view
    }

    abstract fun getItemName(item: T): String

    class ViewHolder(val itemText: TextView)
}