package com.example.yogayu2.ui.home

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yogayu2.data.remote.response.YogaLevelsResponseItem
import com.example.yogayu2.databinding.ItemRowLevelsBinding

class LevelAdapter(private val listLevel: List<YogaLevelsResponseItem>): RecyclerView.Adapter<LevelAdapter.ListViewHolder>() {
    private lateinit var binding: ItemRowLevelsBinding
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemRowLevelsBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        binding = ItemRowLevelsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listLevel.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item: YogaLevelsResponseItem = listLevel.get(position)
        holder.binding.tvLevelName.text = item.name
        holder.binding.desc.text = item.description
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listLevel[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: YogaLevelsResponseItem)
    }
}