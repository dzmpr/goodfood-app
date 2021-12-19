package ru.cookedapp.common.base.list

import androidx.recyclerview.widget.DiffUtil
import ru.cookedapp.common.base.list.data.Item

class CommonDiffUtilCallback(
    private val oldList: List<Item>,
    private val newList: List<Item>,
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem::class != newItem::class) return false
        return oldItem.isItemsSame(newItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem::class != newItem::class) return false
        return oldItem.isContentSame(newItem)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem::class != newItem::class) return null
        return oldItem.calculatePayload(newItem)
    }
}
