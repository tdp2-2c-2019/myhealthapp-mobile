package com.example.admin.repositories.login

import com.example.admin.models.Registration
import com.example.admin.models.SignInForm
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRemoteRepository @Inject constructor(private var loginService: LoginService) {

    fun signIn(form: SignInForm): Observable<String> {
        return loginService.signIn(form)
    }

    fun logIn(dni: String, password: String): Observable<Registration> {
        return loginService.logIn(dni, password)
    }

    fun newPassword(token: String, password: String): Observable<String> {
        return loginService.newPassword(token, password)
    }

    fun sendToken(mail: String): Observable<String> {
        return loginService.sendToken(mail)
    }

}
