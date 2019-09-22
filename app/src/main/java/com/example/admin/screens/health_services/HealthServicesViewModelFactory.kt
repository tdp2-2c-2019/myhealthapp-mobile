package com.example.admin.screens.health_services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.admin.repositories.health_services.HealthServicesRepository

class HealthServicesViewModelFactory(private val repository: HealthServicesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HealthServicesViewModel::class.java)) {
            return HealthServicesViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}