package com.example.admin.screens.new_authorization

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.repositories.health_services.HealthServicesRepository
import com.example.admin.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewAuthorizationViewModel(private var authorizationsRepository: HealthServicesRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    val success = MutableLiveData<Boolean>()

    val error = ObservableField("")

    private val compositeDisposable = CompositeDisposable()

    fun createAuthorization(token: String, title: String) {
        compositeDisposable.add(
            authorizationsRepository.createAuthorization(token, title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.set(true) }
                .doOnComplete { isLoading.set(false) }
                .doOnError { isLoading.set(false) }
                .subscribe(
                    {
                        Log.d("SUCCESS", "CREATE AUTH RQST SUCCESS")
                        success.value = true
                    },
                    {
                        Log.d("ERROR", "CREATE AUTH RQST ERROR", it)
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