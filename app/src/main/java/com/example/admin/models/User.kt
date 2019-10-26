package com.example.admin.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose val dni: Int,
    @Expose val plan: Int,
    @Expose @SerializedName("first_name") val firstName: String,
    @Expose @SerializedName("last_name") val lastName: String
)
