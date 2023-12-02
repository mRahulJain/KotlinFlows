package com.android.kotlinflow.ui.screens.errorHandling.emitAllExceptionHandling

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.kotlinflow.data.model.ApiUser
import com.android.kotlinflow.ui.screens.common.UserListScreen
import com.android.kotlinflow.util.UiState

@Composable
fun EmitAllExceptionHandlingScreen(
    viewModel: EmitAllExceptionHandlingViewModel = hiltViewModel()
) {
    val uiState: UiState<List<ApiUser>> = viewModel.uiState.collectAsState().value
    UserListScreen(uiState)
}