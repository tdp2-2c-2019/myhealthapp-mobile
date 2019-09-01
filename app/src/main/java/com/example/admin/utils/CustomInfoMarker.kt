package com.example.admin.utils

import android.app.Activity
import android.content.Context
import android.view.View
import com.example.admin.R
import com.example.admin.models.ATM
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.atm_info.view.*

class CustomInfoMarker(private val context: Context) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(marker: Marker?): View {

        val mInfoView = (context as Activity).layoutInflater.inflate(R.layout.atm_info, null)
        val mInfoWindow: ATM? = marker?.tag as ATM?

        mInfoView.address_tv.text = mInfoWindow?.address
        mInfoView.bank_tv.text = mInfoWindow?.name
        mInfoView.network_tv.text = mInfoWindow?.network
        mInfoView.terminals_tv.text = mInfoWindow?.atmQuantity.toString()

        return mInfoView
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}