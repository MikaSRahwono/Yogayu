package com.example.yogayu2.ui.profile

import android.app.Activity
import android.media.session.MediaSession.Token
import android.util.Log
import androidx.lifecycle.*
import com.example.yogayu2.data.local.TokenPreferences
import com.example.yogayu2.data.remote.ApiConflig
import com.example.yogayu2.data.remote.response.UserResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class ProfileViewModel(private val pref: TokenPreferences) : ViewModel() {
    private val _user = MutableLiveData<UserResponse>()
    val user : LiveData<UserResponse> = _user

    fun getUser(token: String) {
        val client = ApiConflig.getApiService().getUser("Bearer $token")
        client.enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    _user.value = response.body()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("ProfileViewModel", "onFailure: ${t.message}")
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