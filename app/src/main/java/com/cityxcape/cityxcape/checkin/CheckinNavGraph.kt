package com.cityxcape.cityxcape.checkin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cityxcape.cityxcape.authentication.SignUp
import com.cityxcape.cityxcape.utilities.CheckInScreen


@Composable
fun CheckinNavGraph() {

    val navController = rememberNavController()


    val vm: CheckinViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = CheckInScreen.Checkin.route
    ) {
        composable(route = CheckInScreen.Checkin.route) {
            Checkin(navController, vm)
        }

        composable(route = CheckInScreen.Lounge.route) {
            DigitalLounge(navController, vm)
        }

        composable(route = CheckInScreen.SignUp.route){
            SignUp()
        }
    }
}