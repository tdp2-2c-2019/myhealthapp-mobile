package com.example.admin.repositories.health_services

import com.example.admin.models.HealthService
import io.reactivex.Observable

interface HealthServicesRepository {
    fun healthServices(token: String): Observable<ArrayList<HealthService>>
}