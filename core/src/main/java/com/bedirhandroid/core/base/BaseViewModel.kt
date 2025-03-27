package com.bedirhandroid.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()


    protected fun <T> Flow<T>.collectFlow(
        stateFlow: MutableStateFlow<T>
    ) {
        viewModelScope.launch {
            this@collectFlow
                .onStart { _loadingState.value = true }
                .catch { _errorState.value = it.message }
                .onCompletion { _loadingState.value = false }
                .collect { stateFlow.value = it }
        }
    }
}

