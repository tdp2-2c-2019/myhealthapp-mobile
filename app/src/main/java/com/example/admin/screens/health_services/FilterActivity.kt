package com.example.admin.screens.health_services

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.example.admin.R
import com.example.admin.databinding.ActivityFilterBinding
import dagger.android.support.DaggerAppCompatActivity

class FilterActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        init()
    }

    private fun init() {
        initFilterListener()
        initServicesSpinner()
        initSpecilaitySpinner()
    }

    private fun initFilterListener() {
        binding.filterBtn.setOnClickListener {
            startActivity(Intent(this, HealthServicesActivity::class.java))
        }
    }

    private fun initServicesSpinner() {
        val services = arrayListOf("Profesionales y sanatorios", "Profesionales", "Sanatorios")
        binding.serviceSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, services)
    }

    private fun initSpecilaitySpinner() {
        val specialities = arrayListOf("Pediatría", "Traumatología", "Cardiología", "Kinesiología")
        binding.specialitySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, specialities)
    }


}
