package com.example.yogayu2.ui.leaderboard

import android.util.Log
import androidx.lifecycle.*
import com.example.yogayu2.data.local.TokenPreferences
import com.example.yogayu2.data.remote.ApiConflig
import com.example.yogayu2.data.remote.response.LeaderboardResponse
import com.example.yogayu2.data.remote.response.LeaderboardResponseItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class LeaderboardViewModel(private val pref: TokenPreferences): ViewModel() {
    private val _leaderboard = MutableLiveData<List<LeaderboardResponseItem>>()
    val leaderboard: LiveData<List<LeaderboardResponseItem>> = _leaderboard

    companion object{
        val TAG ="LeaderboardViewModel"
    }

    fun getLeaderboard(token: String) {
        val client = ApiConflig.getApiService().getLeaderboard("Bearer $token")
        client.enqueue(object : retrofit2.Callback<List<LeaderboardResponseItem>> {
            override fun onResponse(
                call: Call<List<LeaderboardResponseItem>>,
                response: Response<List<LeaderboardResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _leaderboard.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<LeaderboardResponseItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getToken() : LiveData<String> {
        Log.d("token", pref.getToken().toString())
        return pref.getToken().asLiveData()
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            pref.saveToken(token)
        }
    }
}