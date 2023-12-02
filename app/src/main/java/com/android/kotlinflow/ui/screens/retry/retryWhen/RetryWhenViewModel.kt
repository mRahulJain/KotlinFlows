package com.android.kotlinflow.ui.screens.retry.retryWhen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.kotlinflow.util.DispatcherProvider
import com.android.kotlinflow.util.UiState
import com.android.kotlinflow.util.doLongRunningTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RetryWhenViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val uiState: StateFlow<UiState<String>> = _uiState

    private val _retryCountState = MutableStateFlow(0)
    private val retryCountState: StateFlow<Int> = _retryCountState

    private val _toastState = MutableSharedFlow<Boolean>()
    val toastState: SharedFlow<Boolean> = _toastState

    init {
        performTask()
    }

    private fun performTask() {
        viewModelScope.launch(dispatcherProvider.main) {
            doLongRunningTask()
                .retryWhen { cause, attempt ->
                    _toastState.emit(true)
                    _retryCountState.value = retryCountState.value + 1
                    return@retryWhen (cause is IOException && attempt < 3)
                }.catch {error ->
                    _uiState.value = UiState.Error("Error received in catch block - $error ; Retried - ${retryCountState.value}")
                }.collect {
                    _uiState.value = UiState.Success("Number received in collect block - $it ; ; Retried - ${retryCountState.value}")
                }
        }
    }

}