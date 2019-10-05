package com.example.admin.screens.forgot_password

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.repositories.login.LoginRepository
import com.example.admin.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EmailViewModel(private var loginRepository: LoginRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    val emailSuccess = MutableLiveData<Boolean>()

    val error = ObservableField("")

    private val compositeDisposable = CompositeDisposable()

    fun sendToken(mail: String) {
        compositeDisposable.add(
            loginRepository.sendToken(mail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.set(true) }
                .doOnComplete { isLoading.set(false) }
                .doOnError { isLoading.set(false) }
                .subscribe(
                    {
                        Log.d("SUCCESS", "SEND TOKEN RQST SUCCESS")
                        emailSuccess.value = true
                    },
                    {
                        Log.d("ERROR", "SEND TOKEN RQST ERROR", it)
                        error.set(Utils.extractError(it))
                        emailSuccess.value = false
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