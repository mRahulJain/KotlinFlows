package com.android.kotlinflow.ui.screens.sharedFlow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.kotlinflow.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedFlowViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider
): ViewModel() {

    private val _toastState = MutableSharedFlow<Boolean>()
    val toastState: SharedFlow<Boolean> = _toastState

    fun showToast() {
        viewModelScope.launch(dispatcherProvider.main) {
            _toastState.emit(true)
        }
    }

}