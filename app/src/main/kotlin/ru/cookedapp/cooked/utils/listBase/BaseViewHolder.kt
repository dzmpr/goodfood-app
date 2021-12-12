package ru.cookedapp.cooked.utils.listBase

import android.content.Context
import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import ru.cookedapp.cooked.utils.listBase.data.Item
import ru.cookedapp.cooked.utils.listBase.data.ItemEvent
import ru.cookedapp.cooked.utils.listBase.data.ItemPayload

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    @Volatile
    protected var holderState = HolderState.DETACHED
        private set

    var holderEventListener: ItemEventListener? = null

    protected val context: Context = itemView.context

    fun bind(item: Item) {
        holderState = HolderState.BINDING
        onBind(item)
        holderState = HolderState.BOUND
    }

    fun partialBind(payload: ItemPayload) {
        holderState = HolderState.BINDING
        onPartialBind(payload)
        holderState = HolderState.BOUND
    }

    protected open fun onBind(item: Item) = Unit

    @CallSuper
    protected open fun onPartialBind(payload: ItemPayload) {
        error("Partial bind was not handled for $payload.")
    }

    @CallSuper
    open fun unbind() {
        holderState = HolderState.DETACHED
    }

    protected fun dispatchClickEvent(item: Item): Boolean {
        val event = ItemEvent.Click(item)
        return dispatchEvent(event)
    }

    protected fun dispatchDeleteEvent(item: Item): Boolean {
        val event = ItemEvent.Delete(item)
        return dispatchEvent(event)
    }

    protected fun dispatchCheckedEvent(item: Item, isChecked: Boolean): Boolean {
        val event = ItemEvent.Checked(item, isChecked)
        return dispatchEvent(event)
    }

    protected fun dispatchEvent(event: ItemEvent): Boolean {
        if (holderState != HolderState.BOUND) return false
        holderEventListener?.onEvent(event) ?: return false
        return true
    }

    enum class HolderState {
        DETACHED,
        BINDING,
        BOUND;
    }
}
