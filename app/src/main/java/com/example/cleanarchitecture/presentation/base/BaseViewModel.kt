package com.example.cleanarchitecture.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    protected fun CoroutineScope.runLaunchIO(
        onError: (suspend (Throwable) -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.value = true
                block()
            }catch (t: Throwable){
                _errorState.value = t.message
                onError?.invoke(t)
            }finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorState.value = null
    }

}