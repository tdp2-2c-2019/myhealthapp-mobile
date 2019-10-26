package com.example.admin.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.admin.screens.health_services.HealthServicesActivity
import com.example.admin.screens.map.MapsActivity
import com.google.android.gms.location.LocationServices

class LocationManager(private val activity: HealthServicesActivity) {

    private val MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1234


    fun checkLocationPermission() {
        if (!hasLocationPermission())
            requestLocationPermission()
        else
            getLastKnownLocation()

    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            MY_PERMISSIONS_REQUEST_FINE_LOCATION
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationClient.lastLocation.apply {

            addOnSuccessListener { location: Location? ->
                if(location === null) loadDefaultLocation()
                else
                    location.let {
                        activity.healthServicesViewModel.setLastKnownLocation(it)
                        activity.initMap()
                        activity.fetchHealthServices()
                    }
            }

            addOnFailureListener {
                loadDefaultLocation()
            }

        }
    }

    private fun loadDefaultLocation() {
        activity.showLocationDialog()
    }

    private fun hasLocationPermission() =
        ContextCompat.checkSelfPermission(
            activity.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_FINE_LOCATION -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getLastKnownLocation()
                else
                    loadDefaultLocation()
            }
        }
    }
}