package com.example.admin.screens.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.admin.R
import com.example.admin.databinding.ActivityLoginBinding
import com.example.admin.screens.health_services.HealthServicesActivity
import com.example.admin.screens.sign_in.SignInActivity
import com.example.admin.utils.Validator
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
        initForgetPasswordListener()
        observeLogIn()
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

    private fun initForgetPasswordListener() {
        binding.forgetPasswordBtn.setOnClickListener {
            showForgetPasswordDialog()
        }
    }

    private fun initLogInListener() {
        binding.loginBtn.setOnClickListener {
            if (Validator.logInValidator(binding)) {
                loginViewModel.login(
                    binding.dniInput.text.toString(),
                    binding.passwordInput.text.toString()
                )
            }
        }
    }

    private fun observeLogIn() {
        loginViewModel.logInSuccess.observe(
            this,
            Observer<Boolean> {
                if (it) goHome()
                else showErrorDialog()
            }
        )
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.log_in_error)
            .setMessage(loginViewModel.error.get())
            .show()
    }

    private fun showForgetPasswordDialog() {
        AlertDialog.Builder(this)
            .setTitle("Proximamente")
            .setMessage(R.string.forget_password_text)
            .show()
    }

    private fun goHome() {
        Toast.makeText(this, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, HealthServicesActivity::class.java))
    }

}
