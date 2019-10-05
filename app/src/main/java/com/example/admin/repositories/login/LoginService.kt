package com.example.admin.repositories.login

import com.example.admin.models.Registration
import com.example.admin.models.SignInForm
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

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

    @FormUrlEncoded
    @POST("api/users/account/recover")
    fun sendToken(
        @Field("mail") mail: String
    ): Observable<String>

    @FormUrlEncoded
    @PUT("api/users/password")
    fun newPassword(
        @Field("token") token: String,
        @Field("password") password: String
    ): Observable<String>
}