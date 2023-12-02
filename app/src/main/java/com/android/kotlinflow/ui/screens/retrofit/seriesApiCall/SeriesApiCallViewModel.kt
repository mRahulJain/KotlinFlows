package com.android.kotlinflow.ui.screens.retrofit.seriesApiCall

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.kotlinflow.data.api.ApiHelper
import com.android.kotlinflow.data.model.ApiUser
import com.android.kotlinflow.util.DispatcherProvider
import com.android.kotlinflow.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesApiCallViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dispatcherProvider: DispatcherProvider
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ApiUser>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ApiUser>>> = _uiState

    init {
        fetchUsers()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchUsers() {
        viewModelScope.launch(dispatcherProvider.main) {
            val allUsers = mutableListOf<ApiUser>()
            apiHelper.getUsers()
                .flatMapConcat { usersFromApi ->
                    allUsers.addAll(usersFromApi)
                    apiHelper.getMoreUsers()
                }
                .flowOn(dispatcherProvider.io)
                .catch { error ->
                    _uiState.value = UiState.Error(error.toString())
                }.collect { moreUsersFromApi ->
                    allUsers.addAll(moreUsersFromApi)
                    _uiState.value = UiState.Success(allUsers)
                }
        }
    }
}