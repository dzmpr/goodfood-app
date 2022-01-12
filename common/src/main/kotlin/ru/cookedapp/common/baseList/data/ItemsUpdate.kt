package ru.cookedapp.common.baseList.data

import androidx.recyclerview.widget.DiffUtil

sealed class ItemsUpdate(
    val newItems: List<Item>,
) {

    class ClearList(
        val oldItemsCount: Int,
    ) : ItemsUpdate(emptyList())

    class FillList(
        val newItemsCount: Int,
        newItems: List<Item>,
    ) : ItemsUpdate(newItems)

    class UpdateList(
        val diffResult: DiffUtil.DiffResult,
        newItems: List<Item>,
    ) : ItemsUpdate(newItems)
}
