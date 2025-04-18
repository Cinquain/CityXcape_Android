package com.cityxcape.cityxcape

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.*
import androidx.navigation.NavDestination
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cityxcape.cityxcape.utilities.BottomNavGraph
import com.cityxcape.cityxcape.utilities.TabScreen


@Composable
fun HomeView() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController = navController)}
    ) { it
        BottomNavGraph(navController)
    }
}


@Composable
fun BottomBar(navController: NavHostController) {

    val screens = listOf(
        TabScreen.Checkin,
        TabScreen.Connections,
        TabScreen.Messages,
        TabScreen.StreetPass
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = Color.Black,
        contentColor = Color.White
    ) {
        screens.forEach { screen ->
            AddItem(screen = screen, currentDestination = currentDestination, navController = navController)
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: TabScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                painter = when(screen) {
                    is TabScreen.Checkin -> painterResource(R.drawable.checkin)
                    is TabScreen.Connections -> painterResource(R.drawable.hexagons3)
                    is TabScreen.Messages -> painterResource(R.drawable.chatbubble)
                    is TabScreen.StreetPass -> painterResource(R.drawable.idcard)
                },
                modifier = Modifier.size(25.dp),
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route)
        }
    )
}