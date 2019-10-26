package com.example.admin.repositories.health_services

import com.example.admin.models.Authorization
import com.example.admin.models.HealthService
import io.reactivex.Observable
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthServicesRemoteRepository @Inject constructor(private var healthServicesService: HealthServicesService) {

    fun healthServices(
        token: String,
        specialization: String,
        query: String,
        distance: Double,
        lat: Double, long: Double
    ): Observable<ArrayList<HealthService>> {
        return healthServicesService.healthServices(token, specialization, query, distance, lat, long)
    }

    fun doctors(
        token: String,
        specialization: String,
        query: String,
        distance: Double,
        lat: Double, long: Double
    ): Observable<ArrayList<HealthService>> {
        return healthServicesService.doctors(token, specialization, query, distance, lat, long)
    }

    fun hospitals(
        token: String,
        specialization: String,
        query: String,
        distance: Double,
        lat: Double, long: Double
    ): Observable<ArrayList<HealthService>> {
        return healthServicesService.hospitals(token, specialization, query, distance, lat, long)
    }

    fun hospitalDetail(id: Int): Observable<HealthService> {
        return healthServicesService.hospitalDetail(id)
    }

    fun doctorDetail(id: Int): Observable<HealthService> {
        return healthServicesService.doctorDetail(id)
    }

    fun authorizations(token: String): Observable<ArrayList<Authorization>> {
        return healthServicesService.authorizations(token)
    }

}
