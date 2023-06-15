package com.example.yogayu2.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yogayu2.data.local.TokenPreferences
import com.example.yogayu2.ui.home.HomeViewModel
import com.example.yogayu2.ui.leaderboard.LeaderboardViewModel
import com.example.yogayu2.ui.level.LevelViewModel
import com.example.yogayu2.ui.login.LoginViewModel
import com.example.yogayu2.ui.pose.PoseViewModel
import com.example.yogayu2.ui.profile.ProfileViewModel

class ViewModelFactory(private val pref: TokenPreferences): ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(pref: TokenPreferences): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(pref)
                }
            }
            return INSTANCE as ViewModelFactory
        }

    }

    @Suppress("UNCHECK_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(pref) as T
        }else if(modelClass.isAssignableFrom(LeaderboardViewModel::class.java)) {
            return LeaderboardViewModel(pref) as T
        }else if (modelClass.isAssignableFrom(LevelViewModel::class.java)) {
            return LevelViewModel(pref) as T
        }else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(pref) as T
        }else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(pref) as T
        }else if (modelClass.isAssignableFrom(PoseViewModel::class.java)) {
            return PoseViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}