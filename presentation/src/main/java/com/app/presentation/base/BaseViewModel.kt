package com.app.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<T> : ViewModel() {

    abstract fun defaultState(): T

    protected val _uiState = MutableStateFlow(defaultState())
    val uiState: StateFlow<T> = _uiState

    private val _navEventChannel = Channel<NavEvent>(capacity = Channel.BUFFERED)
    val navEventFlow: Flow<NavEvent>
        get() = _navEventChannel.receiveAsFlow()

    protected fun newState(stateCopy: (T) -> T) {
        val oldState = _uiState.value!!
        _uiState.value = stateCopy(oldState)
    }

    protected fun requireState(block: (T) -> Unit): Unit = block(_uiState.value!!)

    protected fun requireState(): T = _uiState.value!!
}

sealed interface NavEvent

