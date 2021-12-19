package ru.cookedapp.common.base.list

import ru.cookedapp.common.base.list.data.ItemEvent

fun interface ItemEventListener {

    fun onEvent(event: ItemEvent)
}
