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
import ru.cookedapp.cooked.utils.listBase.data.ItemsUpdate

open class ListAdapter(
    adapterScope: CoroutineScope,
    private val holderFactoryProvider: ViewHolderFactoryProvider,
    private val eventListener: ItemEventListener? = null,
) : RecyclerView.Adapter<BaseViewHolder>() {

    private var items = emptyList<Item>()
    private val listUpdateFlow = MutableSharedFlow<List<Item>>(
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
        newList: List<Item>,
    ): DiffUtil.DiffResult = withContext(Dispatchers.Default) {
        DiffUtil.calculateDiff(CommonDiffUtilCallback(items, newList))
    }

    fun setList(newList: List<Item>) {
        listUpdateFlow.tryEmit(newList)
    }

    override fun getItemViewType(position: Int) = holderFactoryProvider.getViewType(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return holderFactoryProvider.getViewHolderFactory(viewType).create(parent)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            applyPayloads(holder, payloads)
        }
    }

    private fun applyPayloads(holder: BaseViewHolder, payloads: List<Any>) {
        payloads.groupingBy {
            it::class
        }.reduce { _, _, payload ->
            payload
        }.forEach { (_, payload) ->
            holder.partialBind(payload as ItemPayload)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.apply {
            holderEventListener = eventListener
            bind(items[position])
        }
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        holder.apply {
            holderEventListener = null
            unbind()
        }
    }

    override fun getItemCount(): Int = items.size
}
