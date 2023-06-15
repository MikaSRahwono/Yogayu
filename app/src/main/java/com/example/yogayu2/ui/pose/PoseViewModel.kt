package com.example.yogayu2.ui.pose

import android.util.Log
import androidx.lifecycle.*
import com.example.yogayu2.data.local.TokenPreferences
import com.example.yogayu2.data.remote.ApiConflig
import com.example.yogayu2.data.remote.response.DetailPoseResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class PoseViewModel(private val pref: TokenPreferences) : ViewModel() {
    private val _detailPose = MutableLiveData<DetailPoseResponse>()
    val detailPose : LiveData<DetailPoseResponse> = _detailPose

    fun getDetailPose(token: String, id: Int) {
        val client = ApiConflig.getApiService().getDetailPose("Bearer $token", id.toString())
        client.enqueue(object : retrofit2.Callback<DetailPoseResponse> {
            override fun onResponse(
                call: Call<DetailPoseResponse>,
                response: Response<DetailPoseResponse>
            ) {
                if(response.isSuccessful) {
                    _detailPose.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailPoseResponse>, t: Throwable) {
                Log.e("PoseViewModel", "onFailure: ${t.message}")
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