package com.example.yogayu.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.yogayu.data.api.ApiConfig
import com.example.yogayu.data.local.entity.TokenPreferences
import com.example.yogayu.data.model.Login
import com.example.yogayu.data.responses.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: TokenPreferences) : ViewModel()  {
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

    fun login(email: String, pass: String) {
        val client = ApiConfig.getApiService().login(
            Login(
            email = email,
            password = pass,
        )
        )
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _dataLogin.value = response.body()
                    Log.e(TAG, "Berhasil Register")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })    }

}