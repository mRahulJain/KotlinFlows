package com.android.kotlinflow.ui.screens.errorHandling.catchExceptionHandling

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.kotlinflow.data.api.ApiHelper
import com.android.kotlinflow.data.model.ApiUser
import com.android.kotlinflow.util.DispatcherProvider
import com.android.kotlinflow.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatchExceptionHandlingViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dispatcherProvider: DispatcherProvider
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ApiUser>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ApiUser>>> = _uiState

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(dispatcherProvider.main) {
            apiHelper.getUsersWithError()
                .flowOn(dispatcherProvider.io)
                .catch { error ->
                    _uiState.value = UiState.Error(error.toString())
                }.collect { usersFromApi ->
                    _uiState.value = UiState.Success(usersFromApi)
                }
        }
    }

}