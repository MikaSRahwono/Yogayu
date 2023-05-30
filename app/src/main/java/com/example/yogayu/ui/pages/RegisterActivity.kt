package com.example.yogayu.ui.pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.yogayu.databinding.ActivityRegisterBinding
import com.example.yogayu.ui.viewmodel.RegisterViewModel
import com.example.yogayu.ui.viewmodel.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private var email: String = ""
    private var password: String = ""
    private var name: String = ""

    companion object {
        private const val TAG = "RegisterActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addingTextListener()

        val registerViewModel = obtainViewModel(this@RegisterActivity)


        binding.button.setOnClickListener {
//            when (name.isEmpty() or email.isEmpty() or password.isEmpty()) {
//                true -> Toast.makeText(this@RegisterActivity, "Masukkan Data Terlebih Dahulu", Toast.LENGTH_SHORT).show()
//                false -> {
//                    registerViewModel.register(name, email, password)
//                    registerViewModel.isLoading.observe(this) {
//                        showLoading(it)
//                    }
//                }
//            }
        }
    }

    fun addingTextListener() {
        binding.editname.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name = s.toString()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

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
        binding.loginAccount.setOnClickListener(){
            val moveIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
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

    private fun obtainViewModel(activity: AppCompatActivity): RegisterViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, null)
        return ViewModelProvider(activity, factory).get(RegisterViewModel::class.java)
    }
}