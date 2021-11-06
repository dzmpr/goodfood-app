package ru.cookedapp.cooked.ui.manageItems.data

import ru.cookedapp.cooked.utils.listBase.data.Item

data class ManageItemModel(
    override val id: Long,
    override val type: ManageItemsViewType,
    val name: String,
): Item<ManageItemsViewType>
