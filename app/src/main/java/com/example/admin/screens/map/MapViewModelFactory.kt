package com.example.admin.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.admin.repositories.ATMRepository

class MapViewModelFactory(private val repository: ATMRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}