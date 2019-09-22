package com.example.admin.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignInForm(
    @Expose val dni: String,
    @Expose @SerializedName("first_name") val name: String,
    @Expose @SerializedName("last_name") val lastName: String,
    @Expose val mail: String,
    @Expose val password: String,
    @Expose val plan: String
)
