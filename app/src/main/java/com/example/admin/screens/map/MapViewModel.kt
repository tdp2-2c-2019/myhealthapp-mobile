package com.example.admin.screens.map

import android.location.Location
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.models.ATM
import com.example.admin.repositories.atm.ATMRepository
import com.example.admin.utils.CustomInfoMarker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MapViewModel(private var ATMRepository: ATMRepository) : ViewModel() {

    val isLoading = ObservableField(true)

    val banks = MutableLiveData<ArrayList<String>>()

    val ATMs = MutableLiveData<ArrayList<ATM>>()

    private val compositeDisposable = CompositeDisposable()

    private lateinit var mMap: GoogleMap

    private lateinit var lastKnownLocation: Location

    var distance: Double = 0.5

    private var network: String = ""

    private var bank: String = ""

    fun loadATMs() {
        if (network.isEmpty() || bank.isEmpty()) return
        compositeDisposable.add(
            ATMRepository.getATMs(bank, distance, network, lastKnownLocation.latitude, lastKnownLocation.longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    isLoading.set(true)
                    mMap.clear()
                }
                .doOnComplete { isLoading.set(false) }
                .doOnError { isLoading.set(false) }
                .subscribe(
                    { ATMs.value = it },
                    { ATMs.value = null }
                )
        )
    }

    fun loadBanks() {
        if (network.isEmpty()) return
        compositeDisposable.add(
            ATMRepository.getBanks(network)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isLoading.set(true) }
                .doOnComplete { isLoading.set(false) }
                .doOnError { isLoading.set(false) }
                .subscribe(
                    { banks.value = it },
                    { Log.d("ERROR", "BANKS REQUEST ERROR", it) }
                )
        )
    }

    fun setMarkers(ATMs: ArrayList<ATM>) {
        mMap.clear()
        ATMs.forEach {
            val location = LatLng(it.lat, it.long)
            val marker = mMap.addMarker(MarkerOptions().position(location))
            marker.tag = it
        }
    }

    fun onMapReady(googleMap: GoogleMap, customInfoWindow: CustomInfoMarker) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.setInfoWindowAdapter(customInfoWindow)

        lastKnownLocation.let {
            val location = LatLng(it.latitude, it.longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f))
            isLoading.set(false)
        }
    }

    fun setLastKnownLocation(location: Location) {
        lastKnownLocation = location
    }

    fun setDistance(distanceSelected: String) {
        distance = (distanceSelected.toDouble() / 1000)
    }

    fun setNetwork(networkSelected: String) {
        network = networkSelected
    }

    fun setBank(bankSelected: String) {
        bank = bankSelected
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}