package com.example.admin.di

import com.example.admin.repositories.atm.ATMRemoteRepository
import com.example.admin.repositories.atm.ATMRepository
import com.example.admin.repositories.atm.ATMRepositoryImpl
import com.example.admin.repositories.atm.ATMService
import com.example.admin.repositories.login.LoginRemoteRepository
import com.example.admin.repositories.login.LoginRepository
import com.example.admin.repositories.login.LoginRepositoryImpl
import com.example.admin.repositories.login.LoginService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://tdp2-tp0-backend.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideWeatherService(retrofit: Retrofit): ATMService = retrofit.create(ATMService::class.java)

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService = retrofit.create(LoginService::class.java)

    @Provides
    @Singleton
    fun provideWeatherRepository(atmRemoteRepository: ATMRemoteRepository): ATMRepository =
        ATMRepositoryImpl(atmRemoteRepository)

    @Provides
    @Singleton
    fun provideLoginRepository(loginRemoteRepository: LoginRemoteRepository): LoginRepository =
        LoginRepositoryImpl(loginRemoteRepository)

}