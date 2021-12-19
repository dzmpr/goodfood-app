package ru.cookedapp.cooked.ui.manageItems.data

import ru.cookedapp.common.base.list.data.Item

data class ManageItemModel(
    override val id: Long,
    val name: String,
): Item
