package com.android.kotlinflow.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.kotlinflow.ui.screens.errorHandling.catchExceptionHandling.CatchExceptionHandlingScreen
import com.android.kotlinflow.ui.screens.errorHandling.emitAllExceptionHandling.EmitAllExceptionHandlingScreen
import com.android.kotlinflow.ui.screens.retrofit.parallelApiCall.ParallelApiCallScreen
import com.android.kotlinflow.ui.screens.retry.retry.RetryScreen
import com.android.kotlinflow.ui.screens.retry.retryWhen.RetryWhenScreen
import com.android.kotlinflow.ui.screens.room.RoomScreen
import com.android.kotlinflow.ui.screens.retrofit.seriesApiCall.SeriesApiCallScreen
import com.android.kotlinflow.ui.screens.retrofit.singleApiCall.SingleApiCallScreen
import com.android.kotlinflow.ui.screens.sharedFlow.SharedFlowScreen
import com.android.kotlinflow.util.Constants

fun NavGraphBuilder.otherScreenComposable() {
    val route = "${Constants.OTHER_SCREEN}/{${Constants.OTHER_SCREEN_ARGUMENT}}"
    composable(
        route = route,
        arguments = listOf(navArgument(Constants.OTHER_SCREEN_ARGUMENT) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        when (navBackStackEntry.arguments?.getString(Constants.OTHER_SCREEN_ARGUMENT)) {
            Constants.SINGLE_API_CALL_SCREEN -> {
                SingleApiCallScreen()
            }
            Constants.SERIES_API_CALL_SCREEN -> {
                SeriesApiCallScreen()
            }
            Constants.PARALLEL_API_CALL_SCREEN -> {
                ParallelApiCallScreen()
            }
            Constants.ROOM_DB_SCREEN -> {
                RoomScreen()
            }
            Constants.RETRY_SCREEN -> {
                RetryScreen()
            }
            Constants.RETRY_WHEN_SCREEN -> {
                RetryWhenScreen()
            }
            Constants.CATCH_EXCEPTION_HANDLING_SCREEN -> {
                CatchExceptionHandlingScreen()
            }
            Constants.EMIT_ALL_EXCEPTION_HANDLING_SCREEN -> {
                EmitAllExceptionHandlingScreen()
            }
            Constants.SHARED_FLOW_SCREEN -> {
                SharedFlowScreen()
            }
            else -> {
                // do nothing
            }
        }
    }
}