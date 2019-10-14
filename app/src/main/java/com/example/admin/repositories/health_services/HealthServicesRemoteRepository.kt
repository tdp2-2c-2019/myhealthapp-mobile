package com.example.admin.repositories.health_services

import com.example.admin.models.HealthService
import io.reactivex.Observable
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthServicesRemoteRepository @Inject constructor(private var healthServicesService: HealthServicesService) {

    fun healthServices(token: String, specialization: String, query: String): Observable<ArrayList<HealthService>> {
        return healthServicesService.healthServices(token, specialization, query)
    }

    fun doctors(token: String, specialization: String, query: String): Observable<ArrayList<HealthService>> {
        return healthServicesService.doctors(token, specialization, query)
    }

    fun hospitals(token: String, specialization: String, query: String): Observable<ArrayList<HealthService>> {
        return healthServicesService.hospitals(token, specialization, query)
    }

    fun hospitalDetail(id: Int): Observable<HealthService> {
        return healthServicesService.hospitalDetail(id)
    }

    fun doctorDetail(id: Int): Observable<HealthService> {
        return healthServicesService.doctorDetail(id)
    }

}
