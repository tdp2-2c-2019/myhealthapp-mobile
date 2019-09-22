package com.example.admin.screens.health_services

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.admin.R
import com.example.admin.databinding.ActivityHealthServicesBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class HealthServicesActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityHealthServicesBinding

    @Inject
    lateinit var healthServicesViewModelFactory: HealthServicesViewModelFactory

    lateinit var healthServicesViewModel: HealthServicesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_health_services)
        init()
    }

    private fun init() {
        initViewModel()
    }

    private fun initViewModel() {
        healthServicesViewModel = ViewModelProviders.of(this, healthServicesViewModelFactory)
            .get(HealthServicesViewModel::class.java)
        binding.viewModel = healthServicesViewModel
        binding.executePendingBindings()
    }

}
