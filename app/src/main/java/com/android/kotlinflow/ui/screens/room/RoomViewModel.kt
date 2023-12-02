package com.android.kotlinflow.ui.screens.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.kotlinflow.data.api.ApiHelper
import com.android.kotlinflow.data.model.ApiUser
import com.android.kotlinflow.data.room.UserEntity
import com.android.kotlinflow.data.room.UserRepository
import com.android.kotlinflow.util.DispatcherProvider
import com.android.kotlinflow.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val apiHelper: ApiHelper,
    private val userRepository: UserRepository,
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
            userRepository.getAllUsers()
                .flatMapConcat { savedUsersFromApi ->
                    if (savedUsersFromApi.isEmpty()) {
                        return@flatMapConcat apiHelper.getUsers()
                            .flatMapConcat { usersFromApi ->
                                userRepository.addAllUsers(usersFromApi.mapToUserEntity())
                                    .flatMapConcat {
                                        flow { emit(usersFromApi) }
                                    }
                            }
                    } else {
                        return@flatMapConcat flow { emit(savedUsersFromApi.mapToApiUsers()) }
                    }
                }.flowOn(dispatcherProvider.io)
                .catch { error ->
                    _uiState.value = UiState.Error(error.toString())
                }
                .collect { apiUsers ->
                    _uiState.value = UiState.Success(apiUsers)
                }
        }
    }

    private fun List<UserEntity>.mapToApiUsers(): List<ApiUser> {
        return this.map {
            ApiUser(
                id = it.id,
                name = it.name,
                email = it.email,
                avatar = it.avatar
            )
        }
    }

    private fun List<ApiUser>.mapToUserEntity(): List<UserEntity> {
        return this.map {
            UserEntity(
                id = it.id,
                name = it.name,
                email = it.email,
                avatar = it.avatar
            )
        }
    }

}