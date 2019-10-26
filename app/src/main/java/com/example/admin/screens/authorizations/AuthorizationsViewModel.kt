package com.example.admin.screens.authorizations

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.models.Authorization
import com.example.admin.repositories.health_services.HealthServicesRepository
import com.example.admin.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthorizationsViewModel(private var authorizationsRepository: HealthServicesRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    val authorizations = MutableLiveData<ArrayList<Authorization>>()

    val success = MutableLiveData<Boolean>()

    val error = ObservableField("")

    private val compositeDisposable = CompositeDisposable()

    fun fetchAuthorizations(token: String) {
        compositeDisposable.add(
            authorizationsRepository.authorizations(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.set(true) }
                .doOnComplete { isLoading.set(false) }
                .doOnError { isLoading.set(false) }
                .subscribe(
                    {
                        authorizations.value = it
                        success.value = true
                    },
                    {
                        Log.d("ERROR", "AUTHORIZATIONS RQST ERROR", it)
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