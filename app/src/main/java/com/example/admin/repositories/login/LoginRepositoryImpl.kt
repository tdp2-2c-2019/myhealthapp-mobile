package com.example.admin.repositories.login

import com.example.admin.models.Registration
import com.example.admin.models.SignInForm
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl(private val remoteDataSource: LoginRemoteRepository) : LoginRepository {

    override fun logIn(dni: String, password: String): Observable<Registration> {
        return remoteDataSource.logIn(dni, password)
    }

    override fun signIn(form: SignInForm): Observable<String> {
        return remoteDataSource.signIn(form)
    }

}
