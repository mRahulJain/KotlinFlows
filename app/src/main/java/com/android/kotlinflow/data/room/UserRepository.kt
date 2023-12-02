package com.android.kotlinflow.data.room

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class UserRepository @Inject constructor(
    private val userDAO: UserDAO
) {
    fun getAllUsers(): Flow<List<UserEntity>> = flow { emit(userDAO.getAllUsers()) }
    fun addAllUsers(users: List<UserEntity>): Flow<Unit> = flow {
        userDAO.insertAll(users)
        emit(Unit)
    }
}