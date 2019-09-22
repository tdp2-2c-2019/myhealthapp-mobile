package com.example.admin.screens.health_services

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.models.HealthService
import com.example.admin.repositories.health_services.HealthServicesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HealthServicesViewModel(private var healthServicesRepository: HealthServicesRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    val healthServices = MutableLiveData<ArrayList<HealthService>>()

    private val compositeDisposable = CompositeDisposable()

    fun fetchHealthServices(token: String) {
        compositeDisposable.add(
            healthServicesRepository.healthServices(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.set(true) }
                .doOnComplete { isLoading.set(false) }
                .doOnError { isLoading.set(false) }
                .subscribe(
                    { healthServices.value = it },
                    { Log.d("ERROR", "HEALTH SERVICES RQST ERROR", it) }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}