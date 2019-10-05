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

class ForgotPasswordViewModel(private var loginRepository: LoginRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    val forgotPasswordSuccess = MutableLiveData<Boolean>()

    val error = ObservableField("")

    private val compositeDisposable = CompositeDisposable()

    fun newPassword(token: String, password: String) {
        compositeDisposable.add(
            loginRepository.newPassword(token, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.set(true) }
                .doOnComplete { isLoading.set(false) }
                .doOnError { isLoading.set(false) }
                .subscribe(
                    {
                        Log.d("SUCCESS", "FORGOT PASSWORD RQST SUCCESS")
                        forgotPasswordSuccess.value = true
                    },
                    {
                        Log.d("ERROR", "FORGOT PASSWORD RQST ERROR", it)
                        error.set(Utils.extractError(it))
                        forgotPasswordSuccess.value = false
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