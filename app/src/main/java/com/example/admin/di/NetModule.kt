package com.example.admin.di

import com.example.admin.repositories.ATMRemoteRepository
import com.example.admin.repositories.ATMRepository
import com.example.admin.repositories.ATMRepositoryImpl
import com.example.admin.repositories.ATMService
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
    fun provideWeatherRepository(atmRemoteRepository: ATMRemoteRepository): ATMRepository =
        ATMRepositoryImpl(atmRemoteRepository)

}