package com.example.admin.screens.health_services

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.models.HealthService
import com.example.admin.repositories.health_services.HealthServicesRepository
import com.example.admin.utils.Utils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HealthServicesViewModel(private var healthServicesRepository: HealthServicesRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    val healthServices = MutableLiveData<ArrayList<HealthService>>()

    val success = MutableLiveData<Boolean>()

    val error = ObservableField("")

    private val compositeDisposable = CompositeDisposable()

    fun fetchAll(token: String, specialization: String, query: String) {
        fetchHealthServices(
            healthServicesRepository.healthServices(token, specialization, query)
        )
    }

    fun fetchDoctors(token: String, specialization: String, query: String) {
        fetchHealthServices(
            healthServicesRepository.doctors(token, specialization, query)
        )
    }

    fun fetchHospitals(token: String, specialization: String, query: String) {
        fetchHealthServices(
            healthServicesRepository.hospitals(token, specialization, query)
        )
    }

    private fun fetchHealthServices(observable: Observable<ArrayList<HealthService>>) {
        compositeDisposable.add(
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.set(true) }
                .doOnComplete { isLoading.set(false) }
                .doOnError { isLoading.set(false) }
                .subscribe(
                    {
                        healthServices.value = arrayListOf()
                        success.value = true
                    },
                    {
                        Log.d("ERROR", "HEALTH SERVICES RQST ERROR", it)
                        error.set(Utils.extractError(it))
                        success.value = false
                    }
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