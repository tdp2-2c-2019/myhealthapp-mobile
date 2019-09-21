package com.example.admin.repositories.login

import com.example.admin.models.Registration
import com.example.admin.models.SignInForm
import io.reactivex.Observable


interface LoginRepository {
    fun signIn(form: SignInForm) : Observable<Registration>
    fun logIn(dni: String, password: String) : Observable<Registration>
}