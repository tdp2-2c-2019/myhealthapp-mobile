package com.example.admin.screens.new_authorization

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.models.AuthorizationType
import com.example.admin.models.FamilyUser
import com.example.admin.repositories.health_services.HealthServicesRepository
import com.example.admin.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewAuthorizationViewModel(private var authorizationsRepository: HealthServicesRepository) :
    ViewModel() {

    val isLoading = ObservableField(false)

    val success = MutableLiveData<Boolean>()

    val error = ObservableField("")

    private val compositeDisposable = CompositeDisposable()

    val family = MutableLiveData<ArrayList<FamilyUser>>()

    val types = MutableLiveData<ArrayList<AuthorizationType>>()

    fun createAuthorization(
        token: String,
        title: String,
        fromDni: String,
        toDni: String,
        type: Int
    ) {
        compositeDisposable.add(
            authorizationsRepository.createAuthorization(token, title, fromDni, toDni, type)
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

    fun getFamilyGroup(dni: String) {
        compositeDisposable.add(
            authorizationsRepository.getFamilyGroup(dni)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d("SUCCESS", "FAMILY GROUP RQST SUCCESS")
                        family.value = it
                    },
                    {
                        Log.d("ERROR", "FAMILY GROUP RQST ERROR", it)
                        error.set(Utils.extractError(it))
                    }
                )
        )
    }

    fun getTypes() {
        compositeDisposable.add(
            authorizationsRepository.getTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d("SUCCESS", "AUTHS TYPES RQST SUCCESS")
                        types.value = it
                    },
                    {
                        Log.d("ERROR", "AUTHS TYPES RQST ERROR", it)
                        error.set(Utils.extractError(it))
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