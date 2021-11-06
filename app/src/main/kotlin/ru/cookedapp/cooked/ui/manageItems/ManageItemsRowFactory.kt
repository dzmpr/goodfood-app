package ru.cookedapp.cooked.ui.manageItems

import ru.cookedapp.cooked.ui.manageItems.data.ManageItemModel
import ru.cookedapp.cooked.ui.manageItems.data.ManageItemsViewType

class ManageItemsRowFactory {

    fun createManageItem(id: Int, name: String) = ManageItemModel(
        id = id.toLong(),
        type = ManageItemsViewType.ITEM,
        name = name,
    )
}
