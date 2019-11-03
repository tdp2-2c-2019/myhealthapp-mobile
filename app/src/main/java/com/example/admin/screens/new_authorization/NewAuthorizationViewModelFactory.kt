package com.example.admin.screens.new_authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.admin.repositories.health_services.HealthServicesRepository

class NewAuthorizationViewModelFactory(private val repository: HealthServicesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewAuthorizationViewModel::class.java)) {
            return NewAuthorizationViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}