package com.example.admin.screens.health_services

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.admin.repositories.health_services.HealthServicesRepository
import io.reactivex.disposables.CompositeDisposable

class HealthServicesViewModel(private var healthServicesRepository: HealthServicesRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}