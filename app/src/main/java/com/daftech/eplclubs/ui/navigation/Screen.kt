package com.daftech.eplclubs.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object DetailClub : Screen("home/{clubId}") {
        fun createRoute(clubId: Int) = "home/$clubId"
    }
}