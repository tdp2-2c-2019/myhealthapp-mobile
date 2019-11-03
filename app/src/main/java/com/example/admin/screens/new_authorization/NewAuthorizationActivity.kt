package com.example.admin.screens.new_authorization

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View.GONE
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.admin.R
import com.example.admin.databinding.ActivityNewAuthorizationBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class NewAuthorizationActivity : DaggerAppCompatActivity() {

    lateinit var token: String

    lateinit var binding: ActivityNewAuthorizationBinding

    @Inject
    lateinit var newAuthorizationViewModelFactory: NewAuthorizationViewModelFactory

    lateinit var newAuthorizationViewModel: NewAuthorizationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_authorization)
        init()
    }

    private fun init() {
        initViewModel()
        initFamilySpinner()
        initImageListeners()
        observeSuccess()
    }

    private fun initViewModel() {
        newAuthorizationViewModel = ViewModelProviders.of(this, newAuthorizationViewModelFactory)
            .get(NewAuthorizationViewModel::class.java)
        binding.viewModel = newAuthorizationViewModel
        binding.executePendingBindings()
    }

    private fun initFamilySpinner() {
        val family = arrayListOf("", "25333444", "38213789", "24675348")
        binding.familySpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, family)
    }

    private fun initImageListeners() {
        binding.photoFab.setOnClickListener {
            dispatchTakePictureIntent()
        }
        binding.galleryFab.setOnClickListener {
            dispatchGetPictureIntent()
        }
    }

    private fun observeSuccess() {
        newAuthorizationViewModel.success.observe(
            this,
            Observer<Boolean> {
                if (!it) showErrorDialog()
            }
        )
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.services_error)
            .setMessage(newAuthorizationViewModel.error.get())
            .show()
    }

    val REQUEST_IMAGE_CAPTURE = 1
    val SELECT_IMAGE = 2

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun dispatchGetPictureIntent() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(intent, SELECT_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.image.setImageBitmap(imageBitmap)
            hideImageSelection()
        }
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, data?.data)
            binding.image.setImageBitmap(bitmap)
            hideImageSelection()
        }
    }

    private fun hideImageSelection() {
        binding.galleryFab.visibility = GONE
        binding.photoFab.visibility = GONE
        binding.photoLabel.visibility = GONE
        binding.galleryLabel.visibility = GONE
        binding.imageTitle.visibility = GONE
    }
}