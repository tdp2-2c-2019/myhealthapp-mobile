package com.example.admin.repositories.health_services

import com.example.admin.models.*
import io.reactivex.Observable
import java.io.File

interface HealthServicesRepository {
    fun healthServices(token: String, specialization: String, query: String, distance: Double, lat: Double, long: Double): Observable<ArrayList<HealthService>>
    fun doctors(token: String, specialization: String, query: String, distance: Double, lat: Double, long: Double): Observable<ArrayList<HealthService>>
    fun hospitals(token: String, specialization: String, query: String, distance: Double, lat: Double, long: Double): Observable<ArrayList<HealthService>>
    fun hospitalDetail(id: Int, lat: Double, long: Double): Observable<HealthService>
    fun doctorDetail(id: Int, lat: Double, long: Double): Observable<HealthService>
    fun authorizations(token: String): Observable<ArrayList<Authorization>>
    fun createAuthorization(token: String, title: String, from: String, to: String, type: Int, file: File): Observable<AuthResponse>
    fun getFamilyGroup(dni: String): Observable<ArrayList<FamilyUser>>
    fun getTypes(): Observable<ArrayList<AuthorizationType>>
}