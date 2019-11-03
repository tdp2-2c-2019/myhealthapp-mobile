package com.example.admin.repositories.health_services

import com.example.admin.models.Authorization
import com.example.admin.models.HealthService
import io.reactivex.Observable

interface HealthServicesRepository {
    fun healthServices(token: String, specialization: String, query: String, distance: Double, lat: Double, long: Double): Observable<ArrayList<HealthService>>
    fun doctors(token: String, specialization: String, query: String, distance: Double, lat: Double, long: Double): Observable<ArrayList<HealthService>>
    fun hospitals(token: String, specialization: String, query: String, distance: Double, lat: Double, long: Double): Observable<ArrayList<HealthService>>
    fun hospitalDetail(id: Int, lat: Double, long: Double): Observable<HealthService>
    fun doctorDetail(id: Int, lat: Double, long: Double): Observable<HealthService>
    fun authorizations(token: String): Observable<ArrayList<Authorization>>
    fun createAuthorization(token: String, title: String): Observable<Authorization>
}