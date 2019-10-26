package com.example.admin.models

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HealthService(
    @Expose val id: Int,
    @Expose val lon: Double,
    @Expose val lat: Double,
    @Expose val name: String,
    @Expose val address: String,
    @Expose val zone: String,
    @Expose val telephone: String,
    @Expose val mail: String,
    @Expose val specializations: ArrayList<String>,
    @Expose val languages: ArrayList<String>,
    @Expose @SerializedName("minimum_plan") val minimumPlan: String
)

@BindingAdapter("bindArray")
fun bindArray(textView: TextView, string: String) {
    //var subString = string.substring(1, string.length - 1)
    textView.text = string
}