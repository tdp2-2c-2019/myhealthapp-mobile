package com.example.admin.repositories.health_services

import com.example.admin.models.HealthService
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import java.util.ArrayList

interface HealthServicesService {

    @GET("api/health-services")
    fun healthServices(
        @Header("Authorization") authorization: String
    ): Observable<ArrayList<HealthService>>

}