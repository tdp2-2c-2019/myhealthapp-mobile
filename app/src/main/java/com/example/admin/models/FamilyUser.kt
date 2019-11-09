package com.example.admin.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FamilyUser(
    @Expose val dni: String,
    @Expose @SerializedName("affiliate_id") val id: Int,
    @Expose @SerializedName("first_name") val firstName: String,
    @Expose @SerializedName("last_name") val lastName: String
)
