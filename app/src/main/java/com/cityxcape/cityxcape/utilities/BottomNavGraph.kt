package com.cityxcape.cityxcape.utilities

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cityxcape.cityxcape.connections.Connections
import com.cityxcape.cityxcape.discover.Discover
import com.cityxcape.cityxcape.messages.Messages
import com.cityxcape.cityxcape.streetpass.StreetPass


@Composable
fun BottomNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = TabScreen.Checkin.route) { this

        composable(route = TabScreen.Checkin.route) {
            Discover()
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