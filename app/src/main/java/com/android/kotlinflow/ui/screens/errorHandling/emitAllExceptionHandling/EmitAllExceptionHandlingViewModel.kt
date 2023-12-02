package com.android.kotlinflow.ui.screens.errorHandling.emitAllExceptionHandling

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
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmitAllExceptionHandlingViewModel @Inject constructor(
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
            getApiUsersFlow().zip(getMoreApiErrorUsersFlow()) { usersFromApi, moreUsersFromErrorApi ->
                    return@zip usersFromApi + moreUsersFromErrorApi
                }.flowOn(dispatcherProvider.io)
                .catch { error ->
                    _uiState.value = UiState.Error(error.toString())
                }.collect { apiUsers ->
                    _uiState.value = UiState.Success(apiUsers)
                }
        }
    }

    private fun getApiUsersFlow() = apiHelper.getUsers().catch { emitAll(flowOf(emptyList())) }
    private fun getMoreApiErrorUsersFlow() = apiHelper.getUsersWithError().catch { emitAll(flowOf(emptyList())) }

}