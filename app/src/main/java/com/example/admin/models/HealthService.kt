package com.example.admin.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HealthService(
    @Expose val id: Int,
    @Expose val lon: Double,
    @Expose val lat: Double,
    @Expose val name: String,
    @Expose @SerializedName("minimum_plan") val minimumPlan: String
)
