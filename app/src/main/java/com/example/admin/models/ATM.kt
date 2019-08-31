package com.example.admin.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ATM(
    @Expose val id: Int,
    @Expose val long: Double,
    @Expose val lat: Double,
    @Expose @SerializedName("banco") val name: String,
    @Expose @SerializedName("red") val network: String,
    @Expose @SerializedName("ubicacion") val address: String,
    @Expose @SerializedName("terminales") val atmQuantity: Int
)
