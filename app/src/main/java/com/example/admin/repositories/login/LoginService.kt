package com.example.admin.repositories.login

import com.example.admin.models.Registration
import com.example.admin.models.SignInForm
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {

    @POST("api/users")
    fun signIn(
        @Body form: SignInForm
    ): Observable<String>

    @FormUrlEncoded
    @POST("api/login")
    fun logIn(
        @Field("dni") dni: String,
        @Field("password") password: String
    ): Observable<Registration>

}