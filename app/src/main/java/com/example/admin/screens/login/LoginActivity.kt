package com.example.admin.screens.login

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.admin.R
import com.example.admin.databinding.ActivityLoginBinding
import com.example.admin.screens.sign_in.SignInActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory

    lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        init()
    }

    private fun init() {
        initViewModel()
        initLogInListener()
        initSignInListener()
    }

    private fun initViewModel() {
        loginViewModel = ViewModelProviders.of(this, loginViewModelFactory).get(LoginViewModel::class.java)
        binding.viewModel = loginViewModel
        binding.executePendingBindings()
    }

    private fun initSignInListener() {
        binding.signInBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    private fun initLogInListener() {
        binding.loginBtn.setOnClickListener {
            loginViewModel.login(
                binding.dniInput.text.toString(),
                binding.passwordInput.text.toString()
            )
        }
    }

}
