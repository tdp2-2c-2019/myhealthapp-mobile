package com.example.admin.screens.health_services

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
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
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class HealthServicesActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityHealthServicesBinding
    lateinit var filterBinding: FilterDialogBinding

    private val servicesRVAdapter = ServicesRVAdapter(arrayListOf())

    lateinit var filterDialog: AlertDialog

    @Inject
    lateinit var healthServicesViewModelFactory: HealthServicesViewModelFactory

    lateinit var healthServicesViewModel: HealthServicesViewModel

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
        createFilterDialog()
        initHealthServicesRV()
        fetchHealthServices()
        initServicesSpinner()
        initSpecializationSpinner()
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
                it?.let { servicesRVAdapter.replaceData(it) }
            }
        )
    }

    private fun initServicesSpinner() {
        val services = arrayListOf("Profesionales y sanatorios", "Profesionales", "Sanatorios")
        filterBinding.serviceSpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, services)
    }

    private fun initSpecializationSpinner() {
        val specialities = arrayListOf("Pediatría", "Traumatología", "Cardiología", "Kinesiología")
        filterBinding.specialitySpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, specialities)
    }

    private fun fetchHealthServices() {
        val sharedPref = this.getSharedPreferences("TOKEN SP", Context.MODE_PRIVATE) ?: return
        val token = sharedPref.getString("TOKEN", "")
        token?.let {
            healthServicesViewModel.fetchHealthServices(it)
        }
    }

    private fun createFilterDialog() {
        filterDialog = AlertDialog.Builder(this)
            .setTitle("Filtros")
            .setView(filterBinding.root)
            .setPositiveButton(R.string.filter) { dialog, id ->
                // filter...
            }
            .setNegativeButton(R.string.cancel) { dialog, id ->
                dialog.cancel()
            }
            .create()
    }

    private fun showFilterDialog() {
        filterDialog.show()
    }

}
