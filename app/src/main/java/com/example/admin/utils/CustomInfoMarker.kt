package com.example.admin.utils

import android.app.Activity
import android.content.Context
import android.view.View
import com.example.admin.R
import com.example.admin.models.HealthService
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.health_service_info.view.*

class CustomInfoMarker(private val context: Context) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(marker: Marker?): View {

        val mInfoView = (context as Activity).layoutInflater.inflate(R.layout.health_service_info, null)
        val mInfoWindow: HealthService? = marker?.tag as HealthService?

        mInfoView.name_tv.text = mInfoWindow?.name
        mInfoView.address_tv.text = mInfoWindow?.address
        mInfoView.number_tv.text = mInfoWindow?.telephone
        mInfoView.mail_tv.text = mInfoWindow?.mail

        return mInfoView
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}