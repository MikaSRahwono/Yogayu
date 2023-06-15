package com.example.yogayu2.ui.leaderboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yogayu2.data.remote.response.LeaderboardResponseItem
import com.example.yogayu2.data.remote.response.YogaLevelsResponseItem
import com.example.yogayu2.databinding.ItemRowLeaderboardBinding
import com.example.yogayu2.databinding.ItemRowLevelsBinding

class LeaderboardAdapter(private val leaderboard: List<LeaderboardResponseItem>) : RecyclerView.Adapter<LeaderboardAdapter.ListViewHolder>() {
    private lateinit var binding: ItemRowLeaderboardBinding

    class ListViewHolder(var binding: ItemRowLeaderboardBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemRowLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return leaderboard.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item: LeaderboardResponseItem = leaderboard.get(position)
        holder.binding.tvName.text = item.name
        holder.binding.tvPoint.text = item.totalPoints.toString()
    }

}