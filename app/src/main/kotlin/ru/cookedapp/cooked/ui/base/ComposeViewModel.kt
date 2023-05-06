package ru.cookedapp.cooked.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal abstract class ComposeViewModel<State: Any> : ViewModel() {

    protected abstract val initialState: State

    private val _state by lazy { MutableStateFlow(initialState) }
    val state: StateFlow<State> get() = _state.asStateFlow()

    protected fun updateState(updater: (State) -> State) = _state.update(updater)
}
