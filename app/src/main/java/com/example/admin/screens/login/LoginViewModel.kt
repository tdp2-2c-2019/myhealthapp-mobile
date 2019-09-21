package com.example.admin.screens.login

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.repositories.login.LoginRepository
import com.example.admin.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel(private var loginRepository: LoginRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    val logInSuccess = MutableLiveData<Boolean>()

    val error = ObservableField("")

    private val compositeDisposable = CompositeDisposable()

    fun login(dni: String, password: String) {
        compositeDisposable.add(
            loginRepository.logIn(dni, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.set(true) }
                .doOnComplete { isLoading.set(false) }
                .doOnError { isLoading.set(false) }
                .subscribe(
                    {
                        Log.d("SUCCESS", "LOG IN RQST SUCCESS")
                        logInSuccess.value = true
                    },
                    {
                        Log.d("ERROR", "LOG IN RQST ERROR", it)
                        error.set(Utils.extractError(it))
                        logInSuccess.value = false
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