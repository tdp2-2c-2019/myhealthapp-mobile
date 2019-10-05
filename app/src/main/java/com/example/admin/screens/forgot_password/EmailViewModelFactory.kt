package com.example.admin.screens.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.admin.repositories.login.LoginRepository

class EmailViewModelFactory(private val repository: LoginRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmailViewModel::class.java)) {
            return EmailViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}