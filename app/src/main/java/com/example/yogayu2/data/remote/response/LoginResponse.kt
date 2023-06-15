package com.example.yogayu2.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("access")
	val access: String,

	@field:SerializedName("refresh")
	val refresh: String
)
