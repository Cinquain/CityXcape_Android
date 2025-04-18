package com.cityxcape.cityxcape.utilities



sealed class TabScreen(
    val route: String,
    val title: String
) {
    object Checkin: TabScreen(
        route = "checkin",
        title = "Checkin"
    )

    object Connections: TabScreen(
        route = "connections",
        title = "Connections"
    )

    object Messages: TabScreen(
        route = "messages",
        title = "Messages"
    )

    object StreetPass: TabScreen(
        route = "streetpass",
        title = "StreetPass"
    )
}