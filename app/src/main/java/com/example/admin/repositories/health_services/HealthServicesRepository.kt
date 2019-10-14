package com.example.admin.repositories.health_services

import com.example.admin.models.HealthService
import io.reactivex.Observable

interface HealthServicesRepository {
    fun healthServices(token: String, specialization: String, query: String): Observable<ArrayList<HealthService>>
    fun doctors(token: String, specialization: String, query: String): Observable<ArrayList<HealthService>>
    fun hospitals(token: String, specialization: String, query: String): Observable<ArrayList<HealthService>>
    fun hospitalDetail(id: Int): Observable<HealthService>
    fun doctorDetail(id: Int): Observable<HealthService>
}