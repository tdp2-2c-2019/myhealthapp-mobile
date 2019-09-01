package com.example.admin.repositories

import com.example.admin.models.ATM
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ATMService {

    @GET("atms")
    fun getATMs(
        @Query("bank") bank: String,
        @Query("distance") distance: Double,
        @Query("network") network: String,
        @Query("originLat") lat: Double,
        @Query("originLon") long: Double
    ): Observable<ArrayList<ATM>>

    @GET("atms/banks")
    fun getBanks(
        @Query("network") network: String
    ): Observable<ArrayList<String>>

    @GET("atms/networks")
    fun getNetworks(): Observable<ArrayList<String>>

}