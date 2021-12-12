package ru.cookedapp.cooked.ui.manageItems

import javax.inject.Inject
import ru.cookedapp.cooked.ui.manageItems.data.ManageItemModel

class ManageItemsFactory @Inject constructor() {

    fun createManageItem(id: Int, name: String) = ManageItemModel(
        id = id.toLong(),
        name = name,
    )
}
