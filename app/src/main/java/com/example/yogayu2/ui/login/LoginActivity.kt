package com.example.yogayu2.ui.login

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
import com.example.yogayu2.MainActivity
import com.example.yogayu2.data.local.TokenPreferences
import com.example.yogayu2.databinding.ActivityLoginBinding
import com.example.yogayu2.factory.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
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
        loginViewModel = obtainViewModel(this@LoginActivity, pref)
        addingTextListener()

        loginViewModel.getToken().observe(this) {token ->
            if (!token.equals("")) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }

        binding.button.setOnClickListener { view ->
            if(view.id == binding.button.id) {
                btnLoginOnClick()
            }
        }

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

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
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity, pref: TokenPreferences): LoginViewModel {
        val factory = ViewModelFactory.getInstance(pref)
        return ViewModelProvider(activity, factory).get(LoginViewModel::class.java)
    }

    private fun btnLoginOnClick() {
        val email = binding.editemail.text
        val password = binding.editpass.text
        if (email != null && email.toString().isNotEmpty() && password != null && password.toString().isNotEmpty()) {
            loginViewModel.login(email.toString(), password.toString())
            loginViewModel.dataLogin.observe(this) { dataLogin ->
                if (!dataLogin.access.equals("")) {
                    showMessage("login berhasil")
                    loginViewModel.saveToken(dataLogin.access)
                } else {
                    showMessage("gagal login")
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }
}