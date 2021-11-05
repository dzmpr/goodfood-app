package ru.cookedapp.cooked.utils.listBase

import android.content.Context
import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import ru.cookedapp.cooked.utils.listBase.data.Item
import ru.cookedapp.cooked.utils.listBase.data.ItemEvent
import ru.cookedapp.cooked.utils.listBase.data.ItemPayload
import ru.cookedapp.cooked.utils.listBase.data.ItemViewType

abstract class BaseViewHolder<ViewType : ItemViewType>(
    view: View
) : RecyclerView.ViewHolder(view) {

    @Volatile
    protected var holderState = HolderState.DETACHED
        private set

    var holderEventListener: ItemEventListener<ViewType>? = null

    protected val context: Context = itemView.context

    /**
     * This scope is not intended to run heavy coroutines, only for subscribing to view events.
     */
    protected val viewHolderScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    fun bind(item: Item<ViewType>) {
        holderState = HolderState.BINDING
        bindInternal(item)
        holderState = HolderState.BOUND
    }

    fun partialBind(payload: ItemPayload) {
        holderState = HolderState.BINDING
        partialBindInternal(payload)
        holderState = HolderState.BOUND
    }

    protected open fun bindInternal(item: Item<ViewType>) = Unit

    @CallSuper
    protected open fun partialBindInternal(payload: ItemPayload) {
        error("Partial bind was not handled for $payload.")
    }

    @CallSuper
    open fun unbind() {
        holderState = HolderState.DETACHED
    }

    protected fun dispatchClickEvent(item: Item<ViewType>): Boolean {
        val event = ItemEvent.Click(item)
        holderEventListener?.onEvent(event) ?: return false
        return true
    }

    protected fun dispatchDeleteEvent(item: Item<ViewType>): Boolean {
        val event = ItemEvent.Delete(item)
        holderEventListener?.onEvent(event) ?: return false
        return true
    }

    protected fun dispatchCheckedEvent(item: Item<ViewType>, isChecked: Boolean): Boolean {
        val event = ItemEvent.Checked(item, isChecked)
        holderEventListener?.onEvent(event) ?: return false
        return true
    }

    protected fun dispatchCustomEvent(event: ItemEvent<ViewType>): Boolean {
        holderEventListener?.onEvent(event) ?: return false
        return true
    }

    protected fun <T> Flow<T>.holderStateAware(): Flow<T> = filter {
        holderState == HolderState.BOUND
    }

    enum class HolderState {
        DETACHED,
        BINDING,
        BOUND;
    }
}
