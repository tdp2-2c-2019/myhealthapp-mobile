package com.example.admin.screens.map

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.admin.R
import com.example.admin.databinding.ActivityMapsBinding
import com.example.admin.models.ATM
import com.example.admin.utils.LocationManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_maps.*
import javax.inject.Inject

class MapsActivity : DaggerAppCompatActivity(), OnMapReadyCallback {

    lateinit var binding: ActivityMapsBinding

    @Inject
    lateinit var mainViewModelFactory: MapViewModelFactory

    lateinit var mapViewModel: MapViewModel

    private val locationManager = LocationManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps)

        initViewModel()

        locationManager.checkLocationPermission()

        observeATMs()
    }

    private fun initViewModel() {
        mapViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MapViewModel::class.java)
        binding.viewModel = mapViewModel
        binding.executePendingBindings()
    }

    fun initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        initSpinners()
    }

    private fun initSpinners() {
        initDistanceSpinner()
        initNetworkSpinner()
        initBankSpinner()
        observeBanks()
    }

    private fun initDistanceSpinner() {
        val distances = arrayListOf("100", "200", "500", "1000")
        binding.distanceSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, distances)
        binding.distanceSpinner.setSelection(2)
        binding.distanceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                mapViewModel.setDistance(distances[pos])
                mapViewModel.loadATMs()
            }
        }
    }

    private fun initNetworkSpinner() {
        val networks = arrayListOf("","LINK","BANELCO")
        binding.networkSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, networks)
        binding.networkSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                mapViewModel.setNetwork(networks[pos])
                mapViewModel.loadBanks()
                mapViewModel.loadATMs()
            }
        }
    }

    private fun initBankSpinner(filteredBanks: ArrayList<String> = arrayListOf()) {
        val banks = arrayListOf("") + filteredBanks
        binding.bankSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, banks)
        binding.bankSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                mapViewModel.setBank(banks[pos])
                mapViewModel.loadATMs()
            }
        }
    }

    private fun observeBanks() {
        mapViewModel.banks.observe(
            this,
            Observer<ArrayList<String>> { initBankSpinner(it) }
        )
    }

    private fun observeATMs() {
        mapViewModel.ATMs.observe(
            this,
            Observer<ArrayList<ATM>> {
                if (it.isEmpty()) showEmptyDialog()
                else mapViewModel.setMarkers(it)
            }
        )
    }

    private fun showEmptyDialog() {
        AlertDialog.Builder(this)
            .setMessage("No existen resultados. Por favor pruebe con un radio más grande")
            .show()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mapViewModel.onMapReady(googleMap)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        locationManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
