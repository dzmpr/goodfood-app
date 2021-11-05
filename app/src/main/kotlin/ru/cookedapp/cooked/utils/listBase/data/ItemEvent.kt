package ru.cookedapp.cooked.utils.listBase.data

sealed interface ItemEvent<ViewType : ItemViewType> {

    val item: Item<ViewType>

    data class Click<ViewType : ItemViewType>(
        override val item: Item<ViewType>,
    ) : ItemEvent<ViewType>

    data class Checked<ViewType : ItemViewType>(
        override val item: Item<ViewType>,
        val newCheckedState: Boolean,
    ) : ItemEvent<ViewType>

    data class Delete<ViewType : ItemViewType>(
        override val item: Item<ViewType>,
    ) : ItemEvent<ViewType>

    data class Custom<ViewType : ItemViewType>(
        override val item: Item<ViewType>,
        val eventType: Enum<*>,
        val payload: Any? = null,
    ) : ItemEvent<ViewType>
}
