package ru.cookedapp.cooked.utils.listBase

import androidx.recyclerview.widget.DiffUtil
import ru.cookedapp.cooked.utils.listBase.data.Item
import ru.cookedapp.cooked.utils.listBase.data.ItemViewType

class CommonDiffUtilCallback<ViewType : ItemViewType>(
    private val oldList: List<Item<ViewType>>,
    private val newList: List<Item<ViewType>>,
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
