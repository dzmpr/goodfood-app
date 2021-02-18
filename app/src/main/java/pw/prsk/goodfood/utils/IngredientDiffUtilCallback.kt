package pw.prsk.goodfood.utils

import androidx.recyclerview.widget.DiffUtil
import pw.prsk.goodfood.data.IngredientWithMeta

class IngredientDiffUtilCallback(
    private val oldList: List<IngredientWithMeta>,
    private val newList: List<IngredientWithMeta>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].product_id == newList[newItemPosition].product_id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}