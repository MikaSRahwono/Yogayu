package com.example.yogayu2.ui.level

import android.util.Log
import androidx.lifecycle.*
import com.example.yogayu2.data.local.TokenPreferences
import com.example.yogayu2.data.remote.ApiConflig
import com.example.yogayu2.data.remote.response.LevelPoseResponse
import com.example.yogayu2.data.remote.response.LevelPoseResponseItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class LevelViewModel(private val pref: TokenPreferences) : ViewModel() {
    private val _listLevelPose = MutableLiveData<List<LevelPoseResponseItem>>()
    val listLevelPose: LiveData<List<LevelPoseResponseItem>> = _listLevelPose

    fun getLevelPose(token: String, id: Int) {
        val client = ApiConflig.getApiService().getLevelPose("Bearer $token", id.toString())
        client.enqueue(object : retrofit2.Callback<List<LevelPoseResponseItem>> {
            override fun onResponse(
                call: Call<List<LevelPoseResponseItem>>,
                response: Response<List<LevelPoseResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _listLevelPose.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<LevelPoseResponseItem>>, t: Throwable) {
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

    companion object{
        val TAG = "LevelViewModel"
    }
}