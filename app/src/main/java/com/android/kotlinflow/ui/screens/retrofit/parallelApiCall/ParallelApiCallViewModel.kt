package com.android.kotlinflow.ui.screens.retrofit.parallelApiCall

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
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParallelApiCallViewModel @Inject constructor(
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
            apiHelper.getUsers()
                .zip(apiHelper.getMoreUsers()) { usersFromApi, moreUsersFromApi ->
                    return@zip usersFromApi + moreUsersFromApi
                }.flowOn(dispatcherProvider.io)
                .catch { error ->
                    _uiState.value = UiState.Error(error.toString())
                }.collect { allUsersFromApi ->
                    _uiState.value = UiState.Success(allUsersFromApi)
                }
        }
    }

}