package com.example.yogayu2.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailPoseResponse(

	@field:SerializedName("yoga_level")
	val yogaLevel: YogaLevel,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("user_has_done")
	val userHasDone: Boolean,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("first_reward_points")
	val firstRewardPoints: Int
)

