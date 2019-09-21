package com.example.admin.screens.sign_in

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.admin.R
import com.example.admin.databinding.ActivitySignInBinding
import com.example.admin.models.SignInForm
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SignInActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivitySignInBinding

    @Inject
    lateinit var signInViewModelFactory: SignInViewModelFactory

    lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        init()
    }

    private fun init() {
        initViewModel()
        initSignInListener()
        observeSignIn()
    }

    private fun initViewModel() {
        signInViewModel = ViewModelProviders.of(this, signInViewModelFactory).get(SignInViewModel::class.java)
        binding.viewModel = signInViewModel
        binding.executePendingBindings()
    }

    private fun initSignInListener() {
        binding.signInBtn.setOnClickListener {
            signInViewModel.signIn(
                SignInForm(
                    binding.dniInput.text.toString(),
                    binding.nameInput.text.toString(),
                    binding.lastNameInput.text.toString(),
                    binding.mailInput.text.toString(),
                    binding.passwordInput.text.toString()
                )
            )
        }
    }

    private fun observeSignIn() {
        signInViewModel.signInSuccess.observe(
            this,
            Observer<Boolean> {
                if(it) goHome()
                else showErrorDialog()
            }
        )
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.sign_in_error)
            .setMessage(signInViewModel.error.get())
            .show()
    }

    private fun goHome() {
        AlertDialog.Builder(this)
            .setMessage("SUCCESS")
            .show()
    }

}
