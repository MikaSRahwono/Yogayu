package com.example.yogayu.ui.pages

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.yogayu.data.local.entity.TokenPreferences
import com.example.yogayu.databinding.ActivityLoginBinding
import com.example.yogayu.ui.main.MainActivity
import com.example.yogayu.ui.viewmodel.LoginViewModel
import com.example.yogayu.ui.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")


    private var email: String = ""
    private var password: String = ""

    companion object {
        private const val TAG = "LoginActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val pref = TokenPreferences.getInstance(dataStore)
        val loginViewModel = obtainViewModel(this@LoginActivity, pref)

        checkToken(loginViewModel)

        addingTextListener()

        binding.button.setOnClickListener {
            val moveIntent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(moveIntent)
//            when (email.isEmpty() or password.isEmpty()) {
//                true -> Toast.makeText(this@LoginActivity, "Masukkan data login", Toast.LENGTH_SHORT).show()
//                false -> {
//                    loginViewModel.login(email, password)
//                    loginViewModel.isLoading.observe(this) {
//                        showLoading(it)
//                    }
//                    loginViewModel.dataLogin.observe(this) {
//                        if (it.loginResult != null) {
//                            it.loginResult.token?.let { it1 -> loginViewModel.saveToken(it1) }
//                        }
//                    }
//                }
//            }
        }
    }

    fun checkToken(loginViewModel: LoginViewModel) {
//        loginViewModel.getToken().observe(this) {token: String ->
//            if (token != "") {
//                val moveIntent = Intent(this@LoginActivity, MainActivity::class.java)
//                startActivity(moveIntent)
//            }
//        }
    }

    fun addingTextListener() {
        binding.editemail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                email = s.toString()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.editpass.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                password = s.toString()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.createAccount.setOnClickListener(){
            val moveIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(moveIntent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity, pref: TokenPreferences): LoginViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, pref)
        return ViewModelProvider(activity, factory).get(LoginViewModel::class.java)
    }
}