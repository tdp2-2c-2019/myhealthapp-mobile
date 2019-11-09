package com.example.admin.models

import com.google.gson.annotations.Expose

data class Registration(
    @Expose val message: String,
    @Expose val token: String,
    @Expose val dni: String
)
