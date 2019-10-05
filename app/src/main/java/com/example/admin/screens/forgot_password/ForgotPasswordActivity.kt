package com.example.admin.screens.forgot_password

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.admin.R
import com.example.admin.databinding.ActivityForgotPasswordBinding
import com.example.admin.screens.login.LoginActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ForgotPasswordActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityForgotPasswordBinding

    @Inject
    lateinit var forgotPasswordViewModelFactory: ForgotPasswordViewModelFactory

    lateinit var forgotPasswordViewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        init()
    }

    private fun init() {
        initViewModel()
        initConfirmListener()
        observeForgotPassword()
    }

    private fun initViewModel() {
        forgotPasswordViewModel =
            ViewModelProviders.of(this, forgotPasswordViewModelFactory).get(ForgotPasswordViewModel::class.java)
        binding.viewModel = forgotPasswordViewModel
        binding.executePendingBindings()
    }

    private fun initConfirmListener() {
        binding.confirmBtn.setOnClickListener {
            forgotPasswordViewModel.newPassword(
                binding.tokenInput.text.toString(),
                binding.passwordInput.text.toString()
            )
        }
    }

    private fun observeForgotPassword() {
        forgotPasswordViewModel.forgotPasswordSuccess.observe(
            this,
            Observer<Boolean> {
                if (it) goLogin()
                else showErrorDialog()
            }
        )
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.log_in_error)
            .setMessage(forgotPasswordViewModel.error.get())
            .show()
    }

    private fun goLogin() {
        Toast.makeText(this, "¡Cambio de contraseña exitoso!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
