package com.example.admin.repositories.health_services

import com.example.admin.models.*
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

    fun hospitalDetail(id: Int, lat: Double, long: Double): Observable<HealthService> {
        return healthServicesService.hospitalDetail(id, lat, long)
    }

    fun doctorDetail(id: Int, lat: Double, long: Double): Observable<HealthService> {
        return healthServicesService.doctorDetail(id, lat, long)
    }

    fun authorizations(token: String): Observable<ArrayList<Authorization>> {
        return healthServicesService.authorizations(token)
    }

    fun createAuthorization(
        token: String,
        title: String,
        from: String,
        to: String,
        type: Int
    ): Observable<AuthResponse> {
        return healthServicesService.createAuthorization(token, title, from, to, type)
    }

    fun getFamilyGroup(dni: String): Observable<ArrayList<FamilyUser>> {
        return healthServicesService.getFamilyGroup(dni)
    }

    fun getTypes(): Observable<ArrayList<AuthorizationType>> {
        return healthServicesService.getTypes()
    }

}
