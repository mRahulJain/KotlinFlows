package com.android.kotlinflow.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.kotlinflow.ui.screens.MainScreen
import com.android.kotlinflow.util.Constants

fun NavGraphBuilder.mainScreenComposable(
    navigateToOtherScreens: (String) -> Unit
) {
    composable(
        route = Constants.MAIN_SCREEN
    ) {
        MainScreen(navigateToOtherScreens = navigateToOtherScreens)
    }
}