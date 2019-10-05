package com.example.admin.screens.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.admin.repositories.login.LoginRepository

class ForgotPasswordViewModelFactory(private val repository: LoginRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java)) {
            return ForgotPasswordViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}