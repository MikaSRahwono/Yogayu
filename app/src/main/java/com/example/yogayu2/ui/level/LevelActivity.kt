package com.example.yogayu2.ui.level

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yogayu2.data.local.TokenPreferences
import com.example.yogayu2.data.remote.response.LevelPoseResponseItem
import com.example.yogayu2.databinding.ActivityLevelBinding
import com.example.yogayu2.factory.ViewModelFactory
import com.example.yogayu2.ui.pose.PoseActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class LevelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLevelBinding
    private lateinit var rv: RecyclerView
    private lateinit var levelViewModel: LevelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)
        val levelName = intent.getStringExtra("level_name")
        binding.tvLevelName.text = levelName
        rv = binding.rvLevelPose

        val pref = TokenPreferences.getInstance(dataStore)
        levelViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            LevelViewModel(pref)::class.java
        )

        levelViewModel.getToken().observe(this) {token ->
            if (token.isNotEmpty()) {
                levelViewModel.getLevelPose(token, id)
                levelViewModel.listLevelPose.observe(this) {listLevelPose ->
                    setListStories(listLevelPose)
                }
            }
        }
    }

    fun setListStories(listPose: List<LevelPoseResponseItem>) {
        Log.d("setlist", listPose.size.toString())
        rv.hasFixedSize()
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = LevelPoseAdapter(listPose, this)
        rv.adapter = adapter
        adapter.setOnItemClickCallback(object : LevelPoseAdapter.OnItemClickCallback {
            override fun onItemClicked(data: LevelPoseResponseItem) {
                intentSelectedUser(data)
            }
        })
    }

    private fun intentSelectedUser(item: LevelPoseResponseItem) {
        val detailUserIntent = Intent(this@LevelActivity, PoseActivity::class.java)
        detailUserIntent.putExtra("id", item.id)
        startActivity(detailUserIntent)
    }
}