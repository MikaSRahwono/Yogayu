package com.example.yogayu2.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yogayu2.R
import com.example.yogayu2.data.local.TokenPreferences
import com.example.yogayu2.data.remote.response.UserResponse
import com.example.yogayu2.databinding.FragmentProfileBinding
import com.example.yogayu2.factory.ViewModelFactory
import com.example.yogayu2.ui.login.LoginActivity


private val Context.dataStore by preferencesDataStore(name = "token")

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val pref = TokenPreferences.getInstance(requireContext().dataStore)
        val profileViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ProfileViewModel::class.java
        )

        profileViewModel.getToken().observe(this) {token ->
            if (token.isNotEmpty()) {
                profileViewModel.getUser(token)
                profileViewModel.user.observe(this) {user ->
                    setProfileContent(user)
                }
            }
        }

        binding.llLogut.setOnClickListener{
            if (it.id == binding.llLogut.id) {
                profileViewModel.saveToken("")
                val intent = Intent(this.requireActivity(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setProfileContent(user: UserResponse) {
        Glide.with(requireActivity())
            .load(user.profilePictureUrl)
            .into(binding.avaImage)
        binding.tvPoint.text = user.yogaLevel.name
        binding.name.text = user.name
    }
}