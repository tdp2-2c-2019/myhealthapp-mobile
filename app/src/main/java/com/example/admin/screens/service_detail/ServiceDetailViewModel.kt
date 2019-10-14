package com.example.admin.screens.service_detail

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.models.HealthService
import com.example.admin.repositories.health_services.HealthServicesRepository
import com.example.admin.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class ServiceDetailViewModel(private var servicesRepository: HealthServicesRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    val detailSuccess = MutableLiveData<Boolean>()

    val error = ObservableField("")

    val service = ObservableField<HealthService>()

    private val compositeDisposable = CompositeDisposable()

    fun fetchDetail(id: Int) {
        compositeDisposable.add(
            servicesRepository.hospitalDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.set(true) }
                .doOnComplete { isLoading.set(false) }
                .doOnError { isLoading.set(false) }
                .subscribe(
                    {
                        Log.d("SUCCESS", "DETAIL SERVICE RQST SUCCESS")
                        service.set(it)
                        detailSuccess.value = true
                    },
                    {
                        Log.d("ERROR", "DETAIL SERVICE RQST ERROR", it)
                        error.set(Utils.extractError(it))
                        detailSuccess.value = false
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