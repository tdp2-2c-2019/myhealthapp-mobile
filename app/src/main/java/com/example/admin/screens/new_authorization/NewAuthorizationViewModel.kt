package com.example.admin.screens.new_authorization

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.models.Authorization
import com.example.admin.repositories.health_services.HealthServicesRepository
import io.reactivex.disposables.CompositeDisposable

class NewAuthorizationViewModel(private var authorizationsRepository: HealthServicesRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    val success = MutableLiveData<Boolean>()

    val error = ObservableField("")

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}