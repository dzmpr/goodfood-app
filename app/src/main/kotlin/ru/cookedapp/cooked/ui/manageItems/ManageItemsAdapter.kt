package ru.cookedapp.cooked.ui.manageItems

import kotlinx.coroutines.CoroutineScope
import ru.cookedapp.cooked.ui.manageItems.data.ManageItemsViewType
import ru.cookedapp.cooked.ui.manageItems.viewHolders.ManageItemHolder
import ru.cookedapp.cooked.utils.listBase.BaseAdapter
import ru.cookedapp.cooked.utils.listBase.ItemEventListener

class ManageItemsAdapter(
    adapterScope: CoroutineScope,
    eventListener: ItemEventListener<ManageItemsViewType>,
): BaseAdapter<ManageItemsViewType>(adapterScope, eventListener) {

    init {
        registerFactory(ManageItemsViewType.ITEM, ManageItemHolder.getFactory())
    }
}
