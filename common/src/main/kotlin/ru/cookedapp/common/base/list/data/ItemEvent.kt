package ru.cookedapp.common.base.list.data

sealed interface ItemEvent {

    val item: Item

    data class Click(
        override val item: Item,
    ) : ItemEvent

    data class Checked(
        override val item: Item,
        val newCheckedState: Boolean,
    ) : ItemEvent

    data class Delete(
        override val item: Item,
    ) : ItemEvent

    data class Custom(
        override val item: Item,
        val eventType: Enum<*>,
        val payload: Any? = null,
    ) : ItemEvent
}
