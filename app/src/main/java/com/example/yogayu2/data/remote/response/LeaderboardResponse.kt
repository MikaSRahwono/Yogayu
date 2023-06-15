package com.example.yogayu2.data.remote.response

import com.google.gson.annotations.SerializedName

data class LeaderboardResponse(

	@field:SerializedName("LeaderboardResponse")
	val leaderboardResponse: List<LeaderboardResponseItem>
)

data class LeaderboardResponseItem(

	@field:SerializedName("is_active")
	val isActive: Boolean,

	@field:SerializedName("yoga_level")
	val yogaLevel: YogaLevel,

	@field:SerializedName("profile_picture_url")
	val profilePictureUrl: Any,

	@field:SerializedName("total_points")
	val totalPoints: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String
)

