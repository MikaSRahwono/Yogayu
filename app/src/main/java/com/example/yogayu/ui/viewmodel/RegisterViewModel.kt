package com.example.yogayu.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yogayu.data.api.ApiConfig
import com.example.yogayu.data.model.Register
import com.example.yogayu.data.responses.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel (application: Application) : ViewModel() {
    private val _dataRegister = MutableLiveData<RegisterResponse>()
    val dataRegister: LiveData<RegisterResponse> = _dataRegister

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "RegisterActivity"
    }

    fun register(name: String, email: String, pass: String) {
        val client = ApiConfig.getApiService().register(
            Register(
            name = name,
            password = pass,
            email = email
        )
        )
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _dataRegister.value = response.body()
                    Log.e(TAG, "Berhasil Register")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })    }
}
