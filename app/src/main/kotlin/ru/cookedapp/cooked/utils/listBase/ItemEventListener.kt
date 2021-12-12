package ru.cookedapp.cooked.utils.listBase

import ru.cookedapp.cooked.utils.listBase.data.ItemEvent

fun interface ItemEventListener {

    fun onEvent(event: ItemEvent)
}
