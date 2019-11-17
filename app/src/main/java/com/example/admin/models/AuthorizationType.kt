package com.example.admin.models

import com.google.gson.annotations.Expose

data class AuthorizationType(
    @Expose val id: Int,
    @Expose val title: String
)
