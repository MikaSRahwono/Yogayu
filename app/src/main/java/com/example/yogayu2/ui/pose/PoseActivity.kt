package com.example.yogayu2.ui.pose

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.yogayu2.data.local.TokenPreferences
import com.example.yogayu2.data.remote.response.DetailPoseResponse
import com.example.yogayu2.databinding.ActivityPoseBinding
import com.example.yogayu2.factory.ViewModelFactory
import com.example.yogayu2.ui.level.LevelViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class PoseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPoseBinding
    private lateinit var poseViewModel: PoseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)
        val pref = TokenPreferences.getInstance(dataStore)
        poseViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            PoseViewModel(pref)::class.java
        )

        poseViewModel.getToken().observe(this) {token ->
            if (token.isNotEmpty()) {
                poseViewModel.getDetailPose(token, id)
                poseViewModel.detailPose.observe(this) {detailPose ->
                    setDetailContent(detailPose)
                }
            }
        }
    }

    fun setDetailContent(detailPose: DetailPoseResponse) {
        binding.tvPoseName.text = detailPose.name
        Glide.with(this)
            .load(detailPose.imageUrl)
            .into(binding.ivPoseImage)
        binding.tvDesc.text = detailPose.description
        binding.tvPoint.text = "${detailPose.firstRewardPoints} points"
    }
}