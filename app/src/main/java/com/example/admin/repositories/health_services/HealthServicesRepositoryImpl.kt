package com.example.admin.repositories.health_services

import com.example.admin.models.Authorization
import com.example.admin.models.HealthService
import io.reactivex.Observable
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

    override fun hospitalDetail(id: Int): Observable<HealthService> {
        return remoteDataSource.hospitalDetail(id)
    }

    override fun doctorDetail(id: Int): Observable<HealthService> {
        return remoteDataSource.doctorDetail(id)
    }

    override fun authorizations(token: String): Observable<ArrayList<Authorization>> {
        return remoteDataSource.authorizations(token)
    }

}
