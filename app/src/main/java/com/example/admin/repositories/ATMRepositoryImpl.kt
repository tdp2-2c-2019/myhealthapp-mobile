package com.example.admin.repositories

import com.example.admin.models.ATM
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class ATMRepositoryImpl(private val remoteDataSource: ATMRemoteRepository) : ATMRepository {

    override fun getATMs(
        bank: String, distance: Double, network: String, lat: Double, long: Double
    ): Observable<ArrayList<ATM>> {
        return remoteDataSource.getATMs(bank, distance, network, lat, long)
    }

    override fun getBanks(network: String): Observable<ArrayList<String>> {
        return remoteDataSource.getBanks(network)
    }

    override fun getNetworks(): Observable<ArrayList<String>> {
        return remoteDataSource.getNetworks()
    }

}
