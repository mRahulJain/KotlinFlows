package com.android.kotlinflow.ui.screens.retrofit.parallelApiCall

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.kotlinflow.data.model.ApiUser
import com.android.kotlinflow.ui.screens.common.UserListScreen
import com.android.kotlinflow.util.UiState

@Composable
fun ParallelApiCallScreen(
    viewModel: ParallelApiCallViewModel = hiltViewModel()
) {
    val uiState: UiState<List<ApiUser>> = viewModel.uiState.collectAsState().value
    UserListScreen(uiState)
}