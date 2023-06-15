package com.example.yogayu2.ui.login

import android.util.Log
import androidx.lifecycle.*
import com.example.yogayu2.data.local.TokenPreferences
import com.example.yogayu2.data.remote.ApiConflig
import com.example.yogayu2.data.remote.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class LoginViewModel(private val pref: TokenPreferences): ViewModel() {
    private val _dataLogin = MutableLiveData<LoginResponse>()
    val dataLogin: LiveData<LoginResponse> = _dataLogin

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "LoginActivity"
    }

    fun getToken(): LiveData<String> {
        return pref.getToken().asLiveData()
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            pref.saveToken(token)
        }
    }

    fun login(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConflig.getApiService().login(email, password)
        client.enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _dataLogin.value = response.body()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}