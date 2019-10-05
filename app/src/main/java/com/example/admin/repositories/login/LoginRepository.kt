package com.example.admin.repositories.login

import com.example.admin.models.Registration
import com.example.admin.models.SignInForm
import io.reactivex.Observable


interface LoginRepository {
    fun signIn(form: SignInForm) : Observable<String>
    fun logIn(dni: String, password: String) : Observable<Registration>
    fun sendToken(mail: String) : Observable<String>
    fun newPassword(token: String, password: String) : Observable<String>
}