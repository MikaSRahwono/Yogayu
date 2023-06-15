package com.example.yogayu2.ui.level

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yogayu2.data.remote.response.LevelPoseResponse
import com.example.yogayu2.data.remote.response.LevelPoseResponseItem
import com.example.yogayu2.data.remote.response.YogaLevelsResponseItem
import com.example.yogayu2.databinding.ItemRowLevelPoseBinding
import com.example.yogayu2.databinding.ItemRowLevelsBinding

class LevelPoseAdapter(private val listPose: List<LevelPoseResponseItem>, private val activity: Activity): RecyclerView.Adapter<LevelPoseAdapter.ListViewHolder>() {
    private lateinit var binding: ItemRowLevelPoseBinding
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemRowLevelPoseBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemRowLevelPoseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listPose.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item: LevelPoseResponseItem = listPose.get(position)
        Glide.with(activity)
            .load("${item.imageUrl}")
            .into(binding.ivPoseImage)
        holder.binding.tvPoseName.text = item.name
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listPose[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: LevelPoseResponseItem)
    }
}