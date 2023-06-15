package com.example.yogayu2.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yogayu2.data.local.TokenPreferences
import com.example.yogayu2.data.remote.response.YogaLevelsResponseItem
import com.example.yogayu2.databinding.FragmentHomeBinding
import com.example.yogayu2.factory.ViewModelFactory
import com.example.yogayu2.ui.level.LevelActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        rv = binding.rvLevel

        val pref = TokenPreferences.getInstance(dataStore = requireContext().dataStore)
        val homeViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            HomeViewModel::class.java
        )

        val textView: TextView = binding.tvGreeting
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        homeViewModel.getToken().observe(this) {token ->
            if (!token.isEmpty()) {
                homeViewModel.getListYogaLevels(token)
                homeViewModel.listYogaLevels.observe(this) {listYogaLevels ->
                    setListYogaLevel(listYogaLevels)
                }
            }
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setListYogaLevel(listLevel: List<YogaLevelsResponseItem>) {
        Log.d("setlist", listLevel.size.toString())
        rv.hasFixedSize()
        rv.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = LevelAdapter(listLevel)
        rv.adapter = adapter
        adapter.setOnItemClickCallback(object : LevelAdapter.OnItemClickCallback {
            override fun onItemClicked(data: YogaLevelsResponseItem) {
                intentSelectedUser(data)
            }
        })
    }

    private fun intentSelectedUser(item: YogaLevelsResponseItem) {
        val intent = Intent(this@HomeFragment.requireActivity(), LevelActivity::class.java)
        intent.putExtra("id", item.id)
        intent.putExtra("level_name", item.name)
        startActivity(intent)
    }
}