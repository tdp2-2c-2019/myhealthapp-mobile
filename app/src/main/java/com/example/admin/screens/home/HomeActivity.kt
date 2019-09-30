package com.example.admin.screens.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.admin.R
import com.example.admin.databinding.ActivityHomeBinding
import com.example.admin.screens.health_services.FilterActivity
import com.example.admin.screens.health_services.HealthServicesActivity
import dagger.android.support.DaggerAppCompatActivity

class HomeActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        init()
    }

    private fun init() {
        initHealthServicesListener()
        initAuthorizationsListener()
    }

    private fun initHealthServicesListener() {
        binding.healthServiceBtn.setOnClickListener {
            startActivity(Intent(this, FilterActivity::class.java))
        }
    }

    private fun initAuthorizationsListener() {
        binding.authorizationsBtn.setOnClickListener {
            showAuthorizationsDialog()
        }
    }

    private fun showAuthorizationsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Proximamente")
            .setMessage(R.string.authorizations_text)
            .show()
    }

}
