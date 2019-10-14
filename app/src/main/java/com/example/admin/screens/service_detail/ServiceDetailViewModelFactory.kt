package com.example.admin.screens.service_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.admin.repositories.health_services.HealthServicesRepository

class ServiceDetailViewModelFactory(private val repository: HealthServicesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServiceDetailViewModel::class.java)) {
            return ServiceDetailViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}