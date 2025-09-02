package com.criticalgnome.cookbook.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.reflect.KProperty1

abstract class BaseViewModel<S : Any, E: Any>(initialState: S) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val _event = MutableStateFlow<E?>(null)
    val event: StateFlow<E?> = _event.asStateFlow()

    protected fun setState(block: StateBuilder<S>.() -> Unit) {
        val builder = StateBuilder(state.value)
        builder.block()
        _state.value = builder.build()
    }

    protected fun emitEvent(event: E) {
        _event.value = event
    }

    protected fun consumeEvent() {
        _event.value = null
    }

    protected class StateBuilder<S: Any>(private val state: S) {

        private val updates = mutableMapOf<String, Any?>()

        infix fun <T> KProperty1<S, T>.set(value: T) {
            updates[this.name] = value
        }

        fun build(): S {
            return state::class.java.getDeclaredConstructor().newInstance().apply {
                state::class.java.declaredFields.forEach { field ->
                    field.isAccessible = true
                    val value = if (updates.containsKey(field.name)) updates[field.name] else field.get(state)
                    field.set(this, value)
                }
            } as S
        }
    }
}
