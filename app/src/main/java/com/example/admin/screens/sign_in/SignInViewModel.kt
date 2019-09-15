package com.example.admin.screens.sign_in

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.admin.models.SignInForm
import com.example.admin.repositories.login.LoginRepository

class SignInViewModel(private var loginRepository: LoginRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    fun signIn(form: SignInForm) {
        isLoading.set(true)
    }

}