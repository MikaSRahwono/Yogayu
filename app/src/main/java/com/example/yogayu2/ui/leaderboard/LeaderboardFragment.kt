package com.example.yogayu2.ui.leaderboard

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yogayu2.R
import com.example.yogayu2.data.local.TokenPreferences
import com.example.yogayu2.data.remote.response.LeaderboardResponseItem
import com.example.yogayu2.databinding.FragmentHomeBinding
import com.example.yogayu2.databinding.FragmentLeaderboardBinding
import com.example.yogayu2.factory.ViewModelFactory
import com.example.yogayu2.ui.home.HomeViewModel

private val Context.dataStore by preferencesDataStore("token")

class LeaderboardFragment : Fragment() {
    private var _binding: FragmentLeaderboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        rv = binding.rvLeaderboard

        val pref = TokenPreferences.getInstance(requireContext().dataStore)
        val leaderboardViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            LeaderboardViewModel::class.java
        )
        leaderboardViewModel.getToken().observe(this) {token ->
            if (!token.isEmpty()) {
                leaderboardViewModel.getLeaderboard(token)
                leaderboardViewModel.leaderboard.observe(this) {leaderboard ->
                    setLeaderboard(leaderboard)
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setLeaderboard(leaderboard: List<LeaderboardResponseItem>) {
        rv.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = LeaderboardAdapter(leaderboard)
        rv.adapter = adapter
    }
}