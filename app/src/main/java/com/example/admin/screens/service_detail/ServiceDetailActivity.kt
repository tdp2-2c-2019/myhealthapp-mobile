package com.example.admin.screens.service_detail

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.admin.R
import com.example.admin.databinding.ActivityServiceDetailBinding
import com.example.admin.models.HealthService
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ServiceDetailActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityServiceDetailBinding

    @Inject
    lateinit var serviceDetailViewModelFactory: ServiceDetailViewModelFactory

    lateinit var serviceDetailViewModel: ServiceDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_detail)
        init()
    }

    private fun init() {
        initViewModel()
        observeDetail()
        fetchDetail()
    }

    private fun fetchDetail() {
        val id = intent.getIntExtra("ID", 0)
        serviceDetailViewModel.fetchDetail(id)
    }

    private fun initViewModel() {
        serviceDetailViewModel = ViewModelProviders.of(this, serviceDetailViewModelFactory).get(ServiceDetailViewModel::class.java)
        binding.viewModel = serviceDetailViewModel
        binding.executePendingBindings()
    }


    private fun observeDetail() {
        serviceDetailViewModel.detailSuccess.observe(
            this,
            Observer<Boolean> {
                if(!it) showErrorDialog()
            }
        )
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.detail_error)
            .setMessage(serviceDetailViewModel.error.get())
            .show()
    }

}