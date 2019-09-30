package com.example.admin.screens.forgot_password

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.admin.R
import com.example.admin.databinding.ActivityForgotPasswordBinding
import dagger.android.support.DaggerAppCompatActivity

class ForgotPasswordActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        init()
    }

    private fun init() {
    }

    private fun showAuthorizationsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Proximamente")
            .setMessage(R.string.authorizations_text)
            .show()
    }

}
