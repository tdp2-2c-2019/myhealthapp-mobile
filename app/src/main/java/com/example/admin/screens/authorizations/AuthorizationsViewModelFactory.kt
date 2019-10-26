package com.example.admin.screens.authorizations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.admin.repositories.health_services.HealthServicesRepository

class AuthorizationsViewModelFactory(private val repository: HealthServicesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthorizationsViewModel::class.java)) {
            return AuthorizationsViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}