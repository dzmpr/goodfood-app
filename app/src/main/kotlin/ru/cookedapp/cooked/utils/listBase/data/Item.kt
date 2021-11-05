package ru.cookedapp.cooked.utils.listBase.data

interface Item<ViewType : ItemViewType> {

    val id: Long
    val type: ViewType

    fun isItemsSame(item: Item<ViewType>): Boolean = id == item.id

    fun isContentSame(item: Item<ViewType>): Boolean = this == item

    fun calculatePayload(item: Item<ViewType>): ItemPayload? = null
}
