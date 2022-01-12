package ru.cookedapp.cooked.ui.manageItems.data

import ru.cookedapp.common.baseList.data.Item

data class ManageItemModel(
    override val id: Long,
    val name: String,
) : Item
