package com.example.yogayu2.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.yogayu2.data.local.TokenPreferences
import com.example.yogayu2.data.remote.ApiConflig
import com.example.yogayu2.data.remote.response.YogaLevelsResponse
import com.example.yogayu2.data.remote.response.YogaLevelsResponseItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class HomeViewModel(private val pref: TokenPreferences) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Hi Ananta"
    }
    val text: LiveData<String> = _text

    private val _listYogaLevels = MutableLiveData<List<YogaLevelsResponseItem>>()
    val listYogaLevels: LiveData<List<YogaLevelsResponseItem>> = _listYogaLevels

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

    companion object{
        val TAG = "HomeViewModel"
    }
}