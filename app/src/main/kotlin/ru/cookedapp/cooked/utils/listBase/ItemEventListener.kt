package ru.cookedapp.cooked.utils.listBase

import ru.cookedapp.cooked.utils.listBase.data.ItemEvent
import ru.cookedapp.cooked.utils.listBase.data.ItemViewType

fun interface ItemEventListener<ViewType : ItemViewType> {

    fun onEvent(event: ItemEvent<ViewType>)
}
