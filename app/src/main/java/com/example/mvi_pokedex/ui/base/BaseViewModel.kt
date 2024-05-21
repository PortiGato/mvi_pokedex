package com.example.mvi_pokedex.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


abstract class BaseViewModel<S, E, A> : ViewModel() {

    protected abstract val initialViewState: S
    private val _state: MutableStateFlow<S> by lazy { MutableStateFlow(initialViewState) }
     val state: StateFlow<S> get() = _state

    //Channel for send events (or intent)
    private val vmIntent = Channel<E>(Channel.UNLIMITED)

    private val _actions: MutableSharedFlow<A> = MutableSharedFlow(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val actions: Flow<A> get() = _actions

    private val pendingActions: MutableList<A> = mutableListOf()


    //get current app state
    fun getState(): S {
        return state.value
    }

    fun setState(newState: S.() -> S) {
        _state.update(newState)
    }

    fun onStateSelected(state: S) = _state.tryEmit(state)


    //All vm lifecycle observe and event (in channel) and reduce it
    init {
        viewModelScope.launch {
            vmIntent.consumeAsFlow().collect { event ->
                reduce(event)
            }

            _actions.subscriptionCount.firstOrNull { it > 0 }?.let {
                pendingActions.forEach(::dispatchAction)
                pendingActions.clear()
            }
        }
    }


    private val <T> MutableSharedFlow<T>.hasSubscribers: Boolean
        get() = subscriptionCount.value > 0

    protected fun dispatchAction(action: A) {
        if (_actions.hasSubscribers) {
            _actions.tryEmit(action)
        } else {
            pendingActions.add(action)
        }
    }


    //proccess event
    protected abstract fun reduce(event: E)


    //Send event in channel
    fun sendEvent(event: E) {
        viewModelScope.launch {
            vmIntent.send(event)
        }
    }


}