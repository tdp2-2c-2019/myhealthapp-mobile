package com.example.admin.screens.sign_in

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.admin.R
import com.example.admin.databinding.ActivitySignInBinding
import com.example.admin.models.SignInForm
import com.example.admin.utils.Validator
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
        initPlanSpinner()
        observeSignIn()
    }

    private fun initViewModel() {
        signInViewModel = ViewModelProviders.of(this, signInViewModelFactory).get(SignInViewModel::class.java)
        binding.viewModel = signInViewModel
        binding.executePendingBindings()
    }

    private fun initSignInListener() {
        binding.signInBtn.setOnClickListener {
            if(Validator.signInValidator(binding)) {
                signInViewModel.signIn(
                    SignInForm(
                        binding.dniInput.text.toString(),
                        binding.nameInput.text.toString(),
                        binding.lastNameInput.text.toString(),
                        binding.mailInput.text.toString(),
                        binding.passwordInput.text.toString(),
                        binding.planSpinner.selectedItem.toString()
                    )
                )
            }
        }
    }

    private fun initPlanSpinner() {
        val plans = arrayListOf("1", "2", "3", "4")
        binding.planSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, plans)
    }

    private fun observeSignIn() {
        signInViewModel.signInSuccess.observe(
            this,
            Observer<Boolean> {
                if(it) goBack()
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

    private fun goBack() {
        Toast.makeText(this, "¡Registro exitoso!", LENGTH_SHORT).show()
        finish()
    }

}
