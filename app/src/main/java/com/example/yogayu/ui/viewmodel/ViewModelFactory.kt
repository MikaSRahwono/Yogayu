package com.example.yogayu.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yogayu.data.local.entity.TokenPreferences

class ViewModelFactory private constructor(private val mApplication: Application, private val pref: TokenPreferences?) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application, pref: TokenPreferences?): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application, pref)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return pref?.let { LoginViewModel(it) } as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return pref?.let { RegisterViewModel(mApplication) } as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
