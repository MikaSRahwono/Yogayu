package com.example.yogayu2.data.remote

import com.example.yogayu2.data.remote.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("token")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>

    @GET("yoga-levels")
    fun getListYogaLevels(
        @Header("Authorization") token: String
    ) : Call<List<YogaLevelsResponseItem>>

    @GET("users/leaderboard")
    fun getLeaderboard(
        @Header("Authorization") token: String
    ) : Call<List<LeaderboardResponseItem>>

    @GET("yoga-levels/{id}/yoga-poses")
    fun getLevelPose(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Call<List<LevelPoseResponseItem>>

    @GET("user")
    fun getUser(
        @Header("Authorization") token: String
    ) : Call<UserResponse>

    @GET("yoga-poses/{id}")
    fun getDetailPose(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ) : Call<DetailPoseResponse>
}