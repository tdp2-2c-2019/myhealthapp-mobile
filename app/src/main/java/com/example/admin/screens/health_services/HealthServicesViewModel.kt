package com.example.admin.screens.health_services

import android.location.Location
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.models.HealthService
import com.example.admin.repositories.health_services.HealthServicesRepository
import com.example.admin.utils.CustomInfoMarker
import com.example.admin.utils.Utils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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

    fun fetchAll(token: String, specialization: String, query: String, distance: String) {
        fetchHealthServices(
            healthServicesRepository.healthServices(token, specialization, query, distance.toDouble(), lastKnownLocation.latitude, lastKnownLocation.longitude)
        )
    }

    fun fetchDoctors(token: String, specialization: String, query: String, distance: String) {
        fetchHealthServices(
            healthServicesRepository.doctors(token, specialization, query, distance.toDouble(), lastKnownLocation.latitude, lastKnownLocation.longitude)
        )
    }

    fun fetchHospitals(token: String, specialization: String, query: String, distance: String) {
        fetchHealthServices(
            healthServicesRepository.hospitals(token, specialization, query, distance.toDouble(), lastKnownLocation.latitude, lastKnownLocation.longitude)
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
                        healthServices.value = it
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

    // MAP
    private lateinit var mMap: GoogleMap

    private lateinit var lastKnownLocation: Location

    fun onMapReady(googleMap: GoogleMap, customInfoWindow: CustomInfoMarker, listener: GoogleMap.OnInfoWindowClickListener) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.setInfoWindowAdapter(customInfoWindow)
        mMap.setOnInfoWindowClickListener(listener)

        lastKnownLocation.let {
            val location = LatLng(it.latitude, it.longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15.0f))
            isLoading.set(false)
        }
    }

    fun setLastKnownLocation(location: Location) {
        lastKnownLocation = location
    }

    fun getLocation(): Location {
        return lastKnownLocation
    }

    fun setMarkers(services: ArrayList<HealthService>) {
        mMap.clear()
        services.forEach {
            val location = LatLng(it.lat, it.lon)
            val marker = mMap.addMarker(MarkerOptions().position(location))
            marker.tag = it
        }
    }
}