package com.example.admin.repositories.login

import com.example.admin.models.Registration
import com.example.admin.models.SignInForm
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl(private val remoteDataSource: LoginRemoteRepository) : LoginRepository {

    override fun logIn(dni: String, password: String, key: String): Observable<Registration> {
        return remoteDataSource.logIn(dni, password, key)
    }

    override fun signIn(form: SignInForm): Observable<String> {
        return remoteDataSource.signIn(form)
    }

    override fun sendToken(mail: String): Observable<String> {
        return remoteDataSource.sendToken(mail)
    }

    override fun newPassword(token: String, password: String): Observable<String> {
        return remoteDataSource.newPassword(token, password)
    }
}
