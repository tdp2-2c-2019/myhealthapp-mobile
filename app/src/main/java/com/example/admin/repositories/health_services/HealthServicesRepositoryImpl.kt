package com.example.admin.repositories.health_services

import com.example.admin.models.HealthService
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class HealthServicesRepositoryImpl(private val remoteDataSource: HealthServicesRemoteRepository) : HealthServicesRepository {

    override fun healthServices(
        token: String,
        specialization: String,
        query: String
    ): Observable<ArrayList<HealthService>> {
        return remoteDataSource.healthServices(token, specialization, query)
    }

    override fun doctors(token: String, specialization: String, query: String): Observable<ArrayList<HealthService>> {
        return remoteDataSource.doctors(token, specialization, query)
    }

    override fun hospitals(
        token: String,
        specialization: String,
        query: String
    ): Observable<ArrayList<HealthService>> {
        return remoteDataSource.hospitals(token, specialization, query)
    }

}
