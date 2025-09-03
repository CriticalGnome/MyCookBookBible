package com.criticalgnome.cookbook.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<S : Any, E: Any>(initialState: S) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    open val state: StateFlow<S> = _state.asStateFlow()

    private val _event = MutableStateFlow<E?>(null)
    open val event: StateFlow<E?> = _event.asStateFlow()

    protected fun updateState(update: S.() -> S) {
        _state.update { it.update() }
    }

    protected fun emitEvent(event: E) {
        _event.value = event
    }

    fun consumeEvent() {
        _event.value = null
    }
}
