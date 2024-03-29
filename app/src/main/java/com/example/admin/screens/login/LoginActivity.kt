package com.example.admin.screens.login

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.admin.R
import com.example.admin.databinding.ActivityLoginBinding
import com.example.admin.screens.forgot_password.EmailActivity
import com.example.admin.screens.home.HomeActivity
import com.example.admin.screens.sign_in.SignInActivity
import com.example.admin.utils.Validator
import com.google.firebase.iid.FirebaseInstanceId
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory

    lateinit var loginViewModel: LoginViewModel

    lateinit var key: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
            key = task.result?.token.toString()
            Log.d("FIREBASE ID", key)
        }
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
        loginViewModel =
            ViewModelProviders.of(this, loginViewModelFactory).get(LoginViewModel::class.java)
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
            startActivity(Intent(this, EmailActivity::class.java))
        }
    }

    private fun initLogInListener() {
        binding.loginBtn.setOnClickListener {
            if (Validator.logInValidator(binding)) {
                loginViewModel.login(
                    binding.dniInput.text.toString(),
                    binding.passwordInput.text.toString(),
                    key
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

    private fun goHome() {
        saveToken()
        Toast.makeText(this, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun saveToken() {
        val registration = loginViewModel.registration.get()
        val sharedPref = this.getSharedPreferences("TOKEN SP",Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("TOKEN", registration?.token)
            putString("DNI", registration?.dni)
            commit()
        }
    }
}
