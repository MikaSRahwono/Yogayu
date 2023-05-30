package com.example.yogayu.data.api

import com.example.yogayu.data.model.Login
import com.example.yogayu.data.model.Register
import com.example.yogayu.data.responses.LoginResponse
import com.example.yogayu.data.responses.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("login")
    fun login(@Body request: Login): Call<LoginResponse>

    @POST("register")
    fun register(@Body request: Register): Call<RegisterResponse>
}



