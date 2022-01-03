package ru.cookedapp.common.baseList.data

interface Item {

    val id: Long

    fun isItemsSame(item: Item): Boolean = id == item.id

    fun isContentSame(item: Item): Boolean = this == item

    fun calculatePayload(item: Item): ItemPayload? = null
}

typealias Items = Item
