package ru.cookedapp.cooked.utils.listBase

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import ru.cookedapp.cooked.utils.listBase.data.Item
import ru.cookedapp.cooked.utils.listBase.data.ItemPayload
import ru.cookedapp.cooked.utils.listBase.data.ItemViewType
import ru.cookedapp.cooked.utils.listBase.data.ItemsUpdate

open class BaseAdapter<ViewType : ItemViewType>(
    adapterScope: CoroutineScope,
    private val eventListener: ItemEventListener<ViewType>? = null,
) : RecyclerView.Adapter<BaseViewHolder<ViewType>>() {

    private var items = emptyList<Item<ViewType>>()

    private val holderFactories = mutableMapOf<Int, ViewHolderFactory<ViewType>>()
    private val listUpdateFlow = MutableSharedFlow<List<Item<ViewType>>>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        listUpdateFlow.map { newItems ->
            when {
                items.isEmpty() -> ItemsUpdate.FillList(newItems.size, newItems)
                newItems.isEmpty() -> ItemsUpdate.ClearList(items.size)
                else -> ItemsUpdate.UpdateList(calculateDiff(newItems), newItems)
            }
        }.onEach { listUpdate ->
            items = listUpdate.newItems
            when (listUpdate) {
                is ItemsUpdate.ClearList -> notifyItemRangeRemoved(0, listUpdate.oldItemsCount)
                is ItemsUpdate.FillList -> notifyItemRangeInserted(0, listUpdate.newItemsCount)
                is ItemsUpdate.UpdateList -> listUpdate.diffResult.dispatchUpdatesTo(this)
            }
        }.launchIn(adapterScope)
    }

    private suspend fun calculateDiff(
        newList: List<Item<ViewType>>,
    ): DiffUtil.DiffResult = withContext(Dispatchers.Default) {
        DiffUtil.calculateDiff(CommonDiffUtilCallback(items, newList))
    }

    fun setList(newList: List<Item<ViewType>>) {
        listUpdateFlow.tryEmit(newList)
    }

    override fun getItemViewType(position: Int) = items[position].type.value

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewType> {
        val viewHolderFactory = holderFactories[viewType]
            ?: throw IllegalStateException("Factory not found for viewType: $viewType.")
        return viewHolderFactory.create(parent).apply {
            holderEventListener = eventListener
        }
    }

    fun registerFactory(viewType: ItemViewType, factory: ViewHolderFactory<ViewType>) {
        if (viewType.value in holderFactories) {
            error("\"${viewType.value}\" view type already registered.")
        }
        holderFactories[viewType.value] = factory
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewType>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            applyPayloads(holder, payloads)
        }
    }

    private fun applyPayloads(holder: BaseViewHolder<ViewType>, payloads: List<Any>) {
        payloads.groupingBy {
            it::class
        }.reduce { _, _, payload ->
            payload
        }.forEach { (_, payload) ->
            holder.partialBind(payload as ItemPayload)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewType>, position: Int) {
        holder.bind(items[position])
    }

    override fun onViewRecycled(holder: BaseViewHolder<ViewType>) {
        holder.unbind()
    }

    override fun getItemCount(): Int = items.size
}
