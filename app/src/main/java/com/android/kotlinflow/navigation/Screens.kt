package com.android.kotlinflow.navigation

import androidx.navigation.NavHostController
import com.android.kotlinflow.util.Constants

class Screens(navController: NavHostController) {

    val navigateToOtherScreens: (String) -> Unit = {
        navController.navigate(route = "${Constants.OTHER_SCREEN}/$it")
    }

}