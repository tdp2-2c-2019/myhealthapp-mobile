package com.example.admin.screens.health_services

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.R
import com.example.admin.databinding.ActivityHealthServicesBinding
import com.example.admin.databinding.FilterDialogBinding
import com.example.admin.models.HealthService
import com.example.admin.screens.service_detail.ServiceDetailActivity
import com.example.admin.utils.CustomInfoMarker
import com.example.admin.utils.LocationManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class HealthServicesActivity : DaggerAppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnInfoWindowClickListener {

    lateinit var token: String

    lateinit var binding: ActivityHealthServicesBinding
    lateinit var filterBinding: FilterDialogBinding

    private val servicesRVAdapter = ServicesRVAdapter(arrayListOf(), this)

    lateinit var filterDialog: AlertDialog

    @Inject
    lateinit var healthServicesViewModelFactory: HealthServicesViewModelFactory

    lateinit var healthServicesViewModel: HealthServicesViewModel

    private val locationManager = LocationManager(this)

    private val DOCTORS = "Pofesionales"
    private val HOSPITALS = "Sanatorios"
    private val ALL = "Profesionales y sanatorios"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_health_services)
        filterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this), R.layout.filter_dialog, null, false
        )
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            this.queryHint = "Buscar por nombre..."
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }

    override fun onNewIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Log.d("QUERY", query)
            fetchServices(query)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {
                showFilterDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init() {
        initViewModel()
        locationManager.checkLocationPermission()
        initFab()
        createFilterDialog()
        initHealthServicesRV()
        initServicesSpinner()
        initDistanceSpinner()
        initSpecializationSpinner()
        observeSuccess()
    }

    private fun initViewModel() {
        healthServicesViewModel = ViewModelProviders.of(this, healthServicesViewModelFactory)
            .get(HealthServicesViewModel::class.java)
        binding.viewModel = healthServicesViewModel
        binding.executePendingBindings()
    }

    private fun initHealthServicesRV() {
        binding.servicesRv.layoutManager = LinearLayoutManager(this)
        binding.servicesRv.adapter = servicesRVAdapter
        healthServicesViewModel.healthServices.observe(
            this,
            Observer<ArrayList<HealthService>> {
                it?.let {
                    servicesRVAdapter.replaceData(it)
                    healthServicesViewModel.setMarkers(it)
                    if(it.isEmpty()) {
                        binding.emptyView.visibility = VISIBLE
                        binding.servicesRv.visibility = GONE
                    } else {
                        binding.emptyView.visibility = GONE
                        binding.servicesRv.visibility = VISIBLE
                    }
                }
            }
        )
    }

    private fun initServicesSpinner() {
        val services = arrayListOf(ALL, DOCTORS, HOSPITALS)
        filterBinding.serviceSpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, services)
    }

    private fun initDistanceSpinner() {
        val distances = arrayListOf("", "1 km", "2 km", "3 km", "4 km", "5 km")
        filterBinding.distanceSpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, distances)
    }

    private fun initSpecializationSpinner() {
        val specialities = arrayListOf("", "Cardiologia", "Clinica", "Dermatologia", "Odontologia")
        filterBinding.specialitySpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, specialities)
    }

    fun fetchHealthServices() {
        val sharedPref = this.getSharedPreferences("TOKEN SP", Context.MODE_PRIVATE) ?: return
        token = sharedPref.getString("TOKEN", "")
        fetchAllServices("")
    }

    private fun createFilterDialog() {
        filterDialog = AlertDialog.Builder(this)
            .setTitle("Filtros")
            .setView(filterBinding.root)
            .setPositiveButton(R.string.filter) { dialog, _ ->
                fetchServices("")
                dialog.cancel()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .create()
    }

    private fun showFilterDialog() {
        filterDialog.show()
    }

    private fun fetchServices(query: String) {
        when(filterBinding.serviceSpinner.selectedItem) {
            ALL -> { fetchAllServices(query) }
            DOCTORS -> { fetchDoctors(query) }
            HOSPITALS -> { fetchHospitals(query) }
            else -> { fetchAllServices(query) }
        }
    }

    private fun fetchAllServices(query: String) {
        val specialization = filterBinding.specialitySpinner.selectedItem.toString()
        var distance = filterBinding.distanceSpinner.selectedItem.toString()
        if (distance === "") {
            distance = "50"
        } else {
            distance = distance.substring(0, 1)
        }
        token.let {
            healthServicesViewModel.fetchAll(it, specialization, query, distance)
        }
    }

    private fun fetchDoctors(query: String) {
        val specialization = filterBinding.specialitySpinner.selectedItem.toString()
        var distance = filterBinding.distanceSpinner.selectedItem.toString()
        if (distance === "") {
            distance = "50"
        } else {
            distance = distance.substring(0, 1)
        }
        token.let {
            healthServicesViewModel.fetchDoctors(it, specialization, query, distance)
        }
    }

    private fun fetchHospitals(query: String) {
        val specialization = filterBinding.specialitySpinner.selectedItem.toString()
        var distance = filterBinding.distanceSpinner.selectedItem.toString()
        if (distance === "") {
            distance = "50"
        } else {
            distance = distance.substring(0, 1)
        }
        token.let {
            healthServicesViewModel.fetchHospitals(it, specialization, query, distance)
        }
    }

    private fun observeSuccess() {
        healthServicesViewModel.success.observe(
            this,
            Observer<Boolean> {
                if (!it) showErrorDialog()
            }
        )
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.services_error)
            .setMessage(healthServicesViewModel.error.get())
            .show()
    }

    // MAP
    fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val customInfoWindow = CustomInfoMarker(this)
        healthServicesViewModel.onMapReady(googleMap, customInfoWindow, this)
    }

    override fun onInfoWindowClick(marker: Marker?) {
        val id = (marker?.tag as HealthService).id
        val healthCenter = (marker?.tag as HealthService).healthCenter
        goToDetail(id, healthCenter)
    }

    private fun initFab() {
        binding.fab.setOnClickListener {
//            binding.servicesRv.visibility =
//                if (binding.servicesRv.visibility == VISIBLE) INVISIBLE else VISIBLE
            binding.mapLayout.visibility =
                if (binding.mapLayout.visibility == VISIBLE) INVISIBLE else VISIBLE

            if (binding.mapLayout.visibility == VISIBLE)
                binding.fab.setImageResource(R.drawable.list)
            else
                binding.fab.setImageResource(R.drawable.map)
        }
    }

    fun showLocationDialog() {
        AlertDialog.Builder(this)
            .setMessage(R.string.location_error)
            .setPositiveButton("Reintentar") { _, _ -> locationManager.checkLocationPermission()}
            .show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        locationManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun goToDetail(id: Int, healthCenter: Boolean) {
        val intent = Intent(this, ServiceDetailActivity::class.java)
        intent.putExtra("ID", id)
        intent.putExtra("HEALTH_CENTER", healthCenter)
        startActivity(intent)
    }
}
