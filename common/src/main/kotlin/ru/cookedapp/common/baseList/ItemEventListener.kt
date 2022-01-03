package ru.cookedapp.common.baseList

import ru.cookedapp.common.baseList.data.ItemEvent

fun interface ItemEventListener {

    fun onEvent(event: ItemEvent)
}
