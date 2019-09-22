package com.example.admin.screens.health_services

import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.R
import com.example.admin.databinding.ActivityHealthServicesBinding
import com.example.admin.models.HealthService
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class HealthServicesActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityHealthServicesBinding

    private val servicesRVAdapter = ServicesRVAdapter(arrayListOf())

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
        initHealthServicesRV()
        fetchHealthServices()
    }

    private fun initViewModel() {
        healthServicesViewModel = ViewModelProviders.of(this, healthServicesViewModelFactory)
            .get(HealthServicesViewModel::class.java)
        binding.viewModel = healthServicesViewModel
        binding.executePendingBindings()
    }

    private fun initHealthServicesRV() {
        binding.servicesRv.layoutManager = LinearLayoutManager(this)
        binding.servicesRv.adapter = servicesRVAdapter
        healthServicesViewModel.healthServices.observe(
            this,
            Observer<ArrayList<HealthService>> {
                it?.let { servicesRVAdapter.replaceData(it) }
            }
        )
    }

    private fun fetchHealthServices() {
        val sharedPref = this.getSharedPreferences("TOKEN SP",Context.MODE_PRIVATE) ?: return
        val token = sharedPref.getString("TOKEN", "")
        token?.let {
            healthServicesViewModel.fetchHealthServices(it)
        }
    }

}
