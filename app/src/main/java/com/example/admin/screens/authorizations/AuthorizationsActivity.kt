package com.example.admin.screens.authorizations

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.R
import com.example.admin.databinding.ActivityAuthorizationsBinding
import com.example.admin.models.Authorization
import com.example.admin.screens.new_authorization.NewAuthorizationActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class AuthorizationsActivity : DaggerAppCompatActivity() {

    lateinit var token: String

    lateinit var binding: ActivityAuthorizationsBinding

    private val authorizationsRVAdapter = AuthorizationsRVAdapter(arrayListOf())

    @Inject
    lateinit var authorizationsViewModelFactory: AuthorizationsViewModelFactory

    lateinit var authorizationsViewModel: AuthorizationsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authorizations)
        init()
    }

    override fun onResume() {
        super.onResume()
        fetchAuthorizations()
    }

    private fun init() {
        initViewModel()
        initFab()
        initAuthorizationsRV()
        fetchAuthorizations()
        observeSuccess()
    }

    private fun initViewModel() {
        authorizationsViewModel = ViewModelProviders.of(this, authorizationsViewModelFactory)
            .get(AuthorizationsViewModel::class.java)
        binding.viewModel = authorizationsViewModel
        binding.executePendingBindings()
    }

    private fun initAuthorizationsRV() {
        binding.authorizationsRv.layoutManager = LinearLayoutManager(this)
        binding.authorizationsRv.adapter = authorizationsRVAdapter
        authorizationsViewModel.authorizations.observe(
            this,
            Observer<ArrayList<Authorization>> {
                it?.let {
                    authorizationsRVAdapter.replaceData(it)
                    if (it.isEmpty()) {
                        binding.emptyView.visibility = VISIBLE
                        binding.authorizationsRv.visibility = GONE
                    } else {
                        binding.emptyView.visibility = GONE
                        binding.authorizationsRv.visibility = VISIBLE
                    }
                }
            }
        )
    }

    private fun fetchAuthorizations() {
        val sharedPref = this.getSharedPreferences("TOKEN SP", Context.MODE_PRIVATE) ?: return
        token = sharedPref.getString("TOKEN", "")
        authorizationsViewModel.fetchAuthorizations(token)
    }

    private fun observeSuccess() {
        authorizationsViewModel.success.observe(
            this,
            Observer<Boolean> {
                if (!it) showErrorDialog()
            }
        )
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.services_error)
            .setMessage(authorizationsViewModel.error.get())
            .show()
    }

    private fun initFab() {
        binding.fab.setOnClickListener {
            startActivity(Intent(this, NewAuthorizationActivity::class.java))
        }
    }

    private fun showAuthorizationsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Proximamente")
            .setMessage(R.string.authorizations_text)
            .show()
    }
}