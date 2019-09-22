package com.example.admin.repositories.health_services

import com.example.admin.models.HealthService
import io.reactivex.Observable
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthServicesRemoteRepository @Inject constructor(private var healthServicesService: HealthServicesService) {

    fun healthServices(token: String): Observable<ArrayList<HealthService>> {
        return healthServicesService.healthServices(token)
    }

}
