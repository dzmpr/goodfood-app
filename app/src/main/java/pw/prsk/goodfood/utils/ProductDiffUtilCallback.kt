package pw.prsk.goodfood.utils

import androidx.recyclerview.widget.DiffUtil
import pw.prsk.goodfood.data.Product
import pw.prsk.goodfood.data.ProductWithMeta

class ProductDiffUtilCallback(private val oldList: List<ProductWithMeta>, private val newList: List<ProductWithMeta>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}