package com.example.admin.models

data class SignInForm(
    val dni: String,
    val name: String,
    val lastName: String,
    val mail: String,
    val password: String
)
