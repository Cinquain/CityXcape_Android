package com.cityxcape.cityxcape.utilities

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cityxcape.cityxcape.authentication.Onboarding
import com.cityxcape.cityxcape.authentication.SignUp
import com.cityxcape.cityxcape.checkin.Checkin
import com.cityxcape.cityxcape.connections.Connections
import com.cityxcape.cityxcape.checkin.CheckinNavGraph
import com.cityxcape.cityxcape.messages.Messages
import com.cityxcape.cityxcape.streetpass.StreetPass


@Composable
fun BottomNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = TabScreen.Checkin.route) { this

        composable(route = TabScreen.Checkin.route) {
            CheckinNavGraph()
        }

        composable(route = TabScreen.Connections.route) {
            Connections()
        }

        composable(route = TabScreen.Messages.route) {
            Messages()
        }

        composable(route = TabScreen.StreetPass.route) {
            StreetPass()
        }

    }
}