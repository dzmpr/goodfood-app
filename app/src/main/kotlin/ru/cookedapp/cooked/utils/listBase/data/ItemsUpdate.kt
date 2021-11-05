package ru.cookedapp.cooked.utils.listBase.data

import androidx.recyclerview.widget.DiffUtil

sealed class ItemsUpdate<ViewType : ItemViewType>(
    val newItems: List<Item<ViewType>>,
) {

    class ClearList<ViewType : ItemViewType>(
        val oldItemsCount: Int,
    ) : ItemsUpdate<ViewType>(emptyList())

    class FillList<ViewType : ItemViewType>(
        val newItemsCount: Int,
        newItems: List<Item<ViewType>>,
    ) : ItemsUpdate<ViewType>(newItems)

    class UpdateList<ViewType : ItemViewType>(
        val diffResult: DiffUtil.DiffResult,
        newItems: List<Item<ViewType>>,
    ) : ItemsUpdate<ViewType>(newItems)
}
