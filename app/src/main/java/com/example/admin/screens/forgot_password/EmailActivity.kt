package com.example.admin.screens.forgot_password

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.admin.R
import com.example.admin.databinding.ActivityEmailBinding
import com.example.admin.utils.Validator
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class EmailActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityEmailBinding

    @Inject
    lateinit var emailViewModelFactory: EmailViewModelFactory

    lateinit var emailViewModel: EmailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_email)
        init()
    }

    private fun init() {
        initViewModel()
        initConfirmListener()
        observeEmail()
    }

    private fun initViewModel() {
        emailViewModel =
            ViewModelProviders.of(this, emailViewModelFactory).get(EmailViewModel::class.java)
        binding.viewModel = emailViewModel
        binding.executePendingBindings()
    }

    private fun initConfirmListener() {
        binding.confirmBtn.setOnClickListener {
            if (Validator.emailValidator(binding)) {
                emailViewModel.sendToken(
                    binding.mailInput.text.toString()
                )
            }
        }
    }

    private fun observeEmail() {
        emailViewModel.emailSuccess.observe(
            this,
            Observer<Boolean> {
                if (it) goResetPassword()
                else showErrorDialog()
            }
        )
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.email_error)
            .setMessage(emailViewModel.error.get())
            .show()
    }

    private fun goResetPassword() {
        Toast.makeText(this, "¡Email enviado!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }
}
