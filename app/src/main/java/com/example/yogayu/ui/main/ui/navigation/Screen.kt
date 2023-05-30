package com.example.yogayu.ui.main.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Search: Screen("search")
    object Leaderboard: Screen("leaderboard")
    object Profile: Screen("profile")
}
