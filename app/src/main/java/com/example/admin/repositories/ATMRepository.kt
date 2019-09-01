package com.example.admin.repositories

import com.example.admin.models.ATM
import io.reactivex.Observable

interface ATMRepository {
    fun getATMs(bank: String, distance: Double, network: String, lat: Double, long: Double) : Observable<ArrayList<ATM>>
    fun getBanks(network: String): Observable<ArrayList<String>>
    fun getNetworks() : Observable<ArrayList<String>>
}