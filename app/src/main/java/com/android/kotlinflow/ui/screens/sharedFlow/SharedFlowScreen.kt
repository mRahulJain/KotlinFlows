package com.android.kotlinflow.ui.screens.sharedFlow

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SharedFlowScreen(
    viewModel: SharedFlowViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        onClick = { viewModel.showToast() }
    ) {
        Text(text = "Show Toast")
    }

    val toastState = viewModel.toastState
    LaunchedEffect(key1 = toastState) {
        toastState.collect {
            if (it) Toast.makeText(context, "Sample Toast", Toast.LENGTH_SHORT).show()
        }
    }
}