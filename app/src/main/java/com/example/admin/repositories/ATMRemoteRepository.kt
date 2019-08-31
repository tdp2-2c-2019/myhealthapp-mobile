package com.example.admin.repositories

import com.example.admin.models.ATM
import io.reactivex.Observable
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ATMRemoteRepository @Inject constructor(private var atmService: ATMService) {

    fun getATMs(bank: String, distance: Double, network: String, lat: Double, long: Double): Observable<ArrayList<ATM>> {
        return atmService.getATMs(bank, distance, network, lat, long)
    }

    fun getBanks(): Observable<ArrayList<String>> {
        return atmService.getBanks()
    }

    fun getNetworks(): Observable<ArrayList<String>> {
        return atmService.getNetworks()
    }

}
