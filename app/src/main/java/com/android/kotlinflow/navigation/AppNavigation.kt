package com.android.kotlinflow.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.android.kotlinflow.navigation.destination.mainScreenComposable
import com.android.kotlinflow.navigation.destination.otherScreenComposable
import com.android.kotlinflow.util.Constants

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = Constants.MAIN_SCREEN
    ) {
        mainScreenComposable(navigateToOtherScreens = screen.navigateToOtherScreens)
        otherScreenComposable()
    }
}