package com.example.yogayu2.data.remote.response

import com.google.gson.annotations.SerializedName

data class YogaLevelsResponse(

	@field:SerializedName("YogaLevelsResponse")
	val yogaLevelsResponse: List<YogaLevelsResponseItem>
)

data class YogaLevelsResponseItem(

	@field:SerializedName("minimum_points")
	val minimumPoints: Int,

	@field:SerializedName("image_url")
	val imageUrl: Any,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int
)
