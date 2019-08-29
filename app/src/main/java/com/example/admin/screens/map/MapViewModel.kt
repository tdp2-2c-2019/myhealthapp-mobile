package com.example.admin.screens.map

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.admin.repositories.ATMRepository
import io.reactivex.disposables.CompositeDisposable

class MapViewModel(private var ATMRepository: ATMRepository) : ViewModel() {

    val isLoading = ObservableField(true)

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}