package com.android.kotlinflow.ui.screens.retry.retry

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.kotlinflow.ui.screens.common.SingleItemScreen

@Composable
fun RetryScreen(
    viewModel: RetryViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val uiState = viewModel.uiState.collectAsState().value
    SingleItemScreen(uiState = uiState)

    val toastState = viewModel.toastState
    LaunchedEffect(key1 = toastState) {
        toastState.collect {
            if (it) Toast.makeText(context, "Retrying", Toast.LENGTH_SHORT).show()
        }
    }
}