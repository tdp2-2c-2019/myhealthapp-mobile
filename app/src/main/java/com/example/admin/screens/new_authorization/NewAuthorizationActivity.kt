package com.example.admin.screens.new_authorization

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View.GONE
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.admin.R
import com.example.admin.databinding.ActivityNewAuthorizationBinding
import com.example.admin.models.FamilyUser
import com.example.admin.utils.Validator
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class NewAuthorizationActivity : DaggerAppCompatActivity() {

    var hasImage: Boolean = false

    lateinit var token: String
    lateinit var dni: String

    lateinit var binding: ActivityNewAuthorizationBinding

    @Inject
    lateinit var newAuthorizationViewModelFactory: NewAuthorizationViewModelFactory

    lateinit var newAuthorizationViewModel: NewAuthorizationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_authorization)

        val sharedPref = this.getSharedPreferences("TOKEN SP", Context.MODE_PRIVATE) ?: return
        token = sharedPref.getString("TOKEN", "")
        dni = sharedPref.getString("DNI", "")

        init()
    }

    private fun init() {
        initViewModel()
        initFamilySpinner()
        initTypeSpinner()
        initImageListeners()
        initCreateListener()
        observeFamily()
        observeSuccess()
    }

    private fun initViewModel() {
        newAuthorizationViewModel = ViewModelProviders.of(this, newAuthorizationViewModelFactory)
            .get(NewAuthorizationViewModel::class.java)
        binding.viewModel = newAuthorizationViewModel
        binding.executePendingBindings()
    }

    private fun initFamilySpinner(familyFetch: List<String> = arrayListOf()) {
        val family = arrayListOf("") + familyFetch
        binding.familySpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, family)
    }

    private fun initTypeSpinner() {
        val types = arrayListOf("", "Radiografia", "Resonancia", "Ecografia")
        binding.typeSpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types)
    }

    private fun initImageListeners() {
        binding.photoFab.setOnClickListener {
            dispatchTakePictureIntent()
        }
        binding.galleryFab.setOnClickListener {
            dispatchGetPictureIntent()
        }
    }

    private fun initCreateListener() {
        binding.confirmBtn.setOnClickListener {
            token.let {
                if (Validator.authValidator(binding, hasImage)) {
                    newAuthorizationViewModel.createAuthorization(
                        it,
                        binding.titleInput.text.toString(),
                        dni,
                        getDniFromName(binding.familySpinner.selectedItem.toString())
                    )
                }
            }
        }
    }

    private fun getDniFromName(name: String) : String {
        var result = ""
        newAuthorizationViewModel.family.value?.forEach {
            val familyName = it.firstName + " " + it.lastName
            if (familyName == name) {
                result = it.dni
            }
        }
        return result
    }

    private fun observeFamily() {
        newAuthorizationViewModel.family.observe(
            this,
            Observer<ArrayList<FamilyUser>> {
                val filter = it.map { familyUser -> familyUser.firstName + " " + familyUser.lastName }
                initFamilySpinner(filter)
            }
        )
        newAuthorizationViewModel.getFamilyGroup(dni)
    }

    private fun observeSuccess() {
        newAuthorizationViewModel.success.observe(
            this,
            Observer<Boolean> {
                if(it) goBack()
                else showErrorDialog()
            }
        )
    }

    private fun goBack() {
        Toast.makeText(this, "¡Autorización creada!", Toast.LENGTH_SHORT).show()
        finish()
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
            hasImage = true
        }
        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, data?.data)
            binding.image.setImageBitmap(bitmap)
            hideImageSelection()
            hasImage = true
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