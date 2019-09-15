package com.example.admin.screens.login

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.admin.repositories.login.LoginRepository

class LoginViewModel(private var loginRepository: LoginRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    fun login(dni: String, password: String) {

    }

}