package com.android.kotlinflow.data.api

import com.android.kotlinflow.data.model.ApiUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ApiHelper {

    fun getUsers(): Flow<List<ApiUser>>

    fun getMoreUsers(): Flow<List<ApiUser>>

    fun getUsersWithError(): Flow<List<ApiUser>>

}

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override fun getUsers() = flow { emit(apiService.getUsers()) }

    override fun getMoreUsers() = flow { emit(apiService.getMoreUsers()) }

    override fun getUsersWithError() = flow { emit(apiService.getUsersWithError()) }

}