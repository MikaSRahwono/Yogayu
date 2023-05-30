package com.example.yogayu.ui.main.ui.navigation

import com.example.yogayu.R

sealed class BottomBarScreen(
    val title: String,
    val icon: Int,
    val icon_selected: Int,
    val screen: Screen
) {
   //Home
    object Home: BottomBarScreen(
       title = "Home",
       icon = R.drawable.baseline_home_24_blur,
       icon_selected = R.drawable.baseline_home_24,
       screen = Screen.Home
    )

    //Search
    object Search: BottomBarScreen(
        title = "Search",
        icon = R.drawable.baseline_search_24_blur,
        icon_selected = R.drawable.baseline_search_24,
        screen = Screen.Search
    )

    //Leaderboard
    object Leaderboard: BottomBarScreen(
        title = "Leaderboard",
        icon = R.drawable.baseline_leaderboard_24_blur,
        icon_selected = R.drawable.baseline_leaderboard_24,
        screen = Screen.Leaderboard
    )

    //Home
    object Profile: BottomBarScreen(
        title = "Profile",
        icon = R.drawable.baseline_person_24_blur,
        icon_selected = R.drawable.baseline_person_24,
        screen = Screen.Profile
    )
}
