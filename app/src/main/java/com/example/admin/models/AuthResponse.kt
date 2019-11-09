package com.example.admin.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @Expose val id: Int,
    @Expose val title: String,
    @Expose @SerializedName("created_at") val date: String,
    @Expose val status: String,
    @Expose @SerializedName("created_by") val createdBy: Int,
    @Expose @SerializedName("created_for") val createdFor: Int
)
