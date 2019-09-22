package com.example.admin.repositories.health_services

import com.example.admin.models.HealthService
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class HealthServicesRepositoryImpl(private val remoteDataSource: HealthServicesRemoteRepository) : HealthServicesRepository {

    override fun healthServices(token: String): Observable<ArrayList<HealthService>> {
        return remoteDataSource.healthServices(token)
    }

}
