package pw.prsk.goodfood.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import pw.prsk.goodfood.R

class BaseDropdownAdapter<T>(
    context: Context,
    @LayoutRes val resource: Int,
    private val items: List<T>,
    private val getFieldName: ((T) -> String)
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
        viewHolder.itemText.text = getFieldName(items[position])
        return view
    }

    class ViewHolder(val itemText: TextView)
}