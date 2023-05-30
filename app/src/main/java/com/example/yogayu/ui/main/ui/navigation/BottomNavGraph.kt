package com.example.yogayu.ui.main.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yogayu.ui.main.ui.screen.HomeScreen
import com.example.yogayu.ui.main.ui.screen.LeaderboardScreen
import com.example.yogayu.ui.main.ui.screen.ProfileScreen
import com.example.yogayu.ui.main.ui.screen.SearchScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController, startDestination = BottomBarScreen.Home.screen.route
    ) {
        composable(route = BottomBarScreen.Home.screen.route){
            HomeScreen()
        }

        composable(route = BottomBarScreen.Search.screen.route){
            SearchScreen()
        }

        composable(route = BottomBarScreen.Leaderboard.screen.route){
            LeaderboardScreen()
        }

        composable(route = BottomBarScreen.Profile.screen.route){
            ProfileScreen()
        }
    }
}