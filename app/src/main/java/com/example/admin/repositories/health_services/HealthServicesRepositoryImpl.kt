package com.example.admin.repositories.health_services

import com.example.admin.models.*
import io.reactivex.Observable
import java.io.File
import javax.inject.Singleton

@Singleton
class HealthServicesRepositoryImpl(private val remoteDataSource: HealthServicesRemoteRepository) :
    HealthServicesRepository {

    override fun healthServices(
        token: String,
        specialization: String,
        query: String,
        distance: Double,
        lat: Double, long: Double
    ): Observable<ArrayList<HealthService>> {
        return remoteDataSource.healthServices(token, specialization, query, distance, lat, long)
    }

    override fun doctors(
        token: String,
        specialization: String,
        query: String,
        distance: Double,
        lat: Double, long: Double
    ): Observable<ArrayList<HealthService>> {
        return remoteDataSource.doctors(token, specialization, query, distance, lat, long)
    }

    override fun hospitals(
        token: String,
        specialization: String,
        query: String,
        distance: Double,
        lat: Double, long: Double
    ): Observable<ArrayList<HealthService>> {
        return remoteDataSource.hospitals(token, specialization, query, distance, lat, long)
    }

    override fun hospitalDetail(id: Int, lat: Double, long: Double): Observable<HealthService> {
        return remoteDataSource.hospitalDetail(id, lat, long)
    }

    override fun doctorDetail(id: Int, lat: Double, long: Double): Observable<HealthService> {
        return remoteDataSource.doctorDetail(id, lat, long)
    }

    override fun authorizations(token: String): Observable<ArrayList<Authorization>> {
        return remoteDataSource.authorizations(token)
    }

    override fun createAuthorization(token: String, title: String, from: String, to: String, type: Int, file: File): Observable<AuthResponse> {
        return remoteDataSource.createAuthorization(token, title, from, to, type, file)
    }

    override fun getFamilyGroup(dni: String): Observable<ArrayList<FamilyUser>> {
        return remoteDataSource.getFamilyGroup(dni)
    }

    override fun getTypes(): Observable<ArrayList<AuthorizationType>> {
        return remoteDataSource.getTypes()
    }
}
