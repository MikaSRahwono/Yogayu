package com.example.yogayu2.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.yogayu2.data.local.TokenPreferences
import com.example.yogayu2.data.remote.ApiConflig
import com.example.yogayu2.data.remote.response.UserResponse
import com.example.yogayu2.data.remote.response.YogaLevelsResponse
import com.example.yogayu2.data.remote.response.YogaLevelsResponseItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class HomeViewModel(private val pref: TokenPreferences) : ViewModel() {

    private val _listYogaLevels = MutableLiveData<List<YogaLevelsResponseItem>>()
    val listYogaLevels: LiveData<List<YogaLevelsResponseItem>> = _listYogaLevels

    private val _user = MutableLiveData<UserResponse>()
    val user : LiveData<UserResponse> = _user

    fun getListYogaLevels(token: String) {
        val client = ApiConflig.getApiService().getListYogaLevels("Bearer $token")
        client.enqueue(object : retrofit2.Callback<List<YogaLevelsResponseItem>> {
            override fun onResponse(
                call: Call<List<YogaLevelsResponseItem>>,
                response: Response<List<YogaLevelsResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _listYogaLevels.value = response.body()
                }else{
                    Log.e(TAG, "OnFailure")
                }
            }

            override fun onFailure(call: Call<List<YogaLevelsResponseItem>>, t: Throwable) {
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

    fun getUser(token: String) {
        val client = ApiConflig.getApiService().getUser("Bearer $token")
        client.enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    _user.value = response.body()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object{
        val TAG = "HomeViewModel"
    }
}