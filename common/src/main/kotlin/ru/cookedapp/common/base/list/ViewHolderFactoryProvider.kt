package ru.cookedapp.common.base.list

import kotlin.reflect.KClass
import ru.cookedapp.common.base.list.data.Item

class ViewHolderFactoryProvider(vararg factories: Pair<KClass<out Item>, ViewHolderFactory>) {

    private val classToViewType = mutableMapOf<KClass<out Item>, Int>()
    private val viewTypeToFactory = mutableListOf<ViewHolderFactory>()

    init {
        factories.forEachIndexed { viewType, (itemClass, viewHolderFactory) ->
            classToViewType[itemClass] = viewType
            viewTypeToFactory.add(viewHolderFactory)
        }
    }

    fun getViewType(item: Item): Int {
        val viewType = classToViewType.getOrDefault(item::class, null) ?: run {
            val superClass = classToViewType.keys.find { it.isInstance(item) }
                ?: error("ViewHolder factory for $item superclass not found.")
            classToViewType.getValue(superClass)
        }
        return viewType
    }

    fun getViewHolderFactory(viewType: Int): ViewHolderFactory {
        val factory = viewTypeToFactory.getOrNull(viewType) ?: run {
            val itemClass = classToViewType.filterValues { vt -> vt == viewType }.keys.first()
            error("ViewHolder factory for view type $viewType ($itemClass) not found.")
        }
        return factory
    }
}
