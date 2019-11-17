package com.example.admin.repositories.health_services

import com.example.admin.models.*
import io.reactivex.Observable
import retrofit2.http.*
import java.util.ArrayList

interface HealthServicesService {

    @GET("api/health-services")
    fun healthServices(
        @Header("Authorization") authorization: String,
        @Query("specialization") specialization: String,
        @Query("name") query: String,
        @Query("distance") distance: Double,
        @Query("originLat") lat: Double,
        @Query("originLon") long: Double
    ): Observable<ArrayList<HealthService>>

    @GET("api/health-services/doctors")
    fun doctors(
        @Header("Authorization") authorization: String,
        @Query("specialization") specialization: String,
        @Query("name") query: String,
        @Query("distance") distance: Double,
        @Query("originLat") lat: Double,
        @Query("originLon") long: Double

    ): Observable<ArrayList<HealthService>>

    @GET("api/health-services/hospitals")
    fun hospitals(
        @Header("Authorization") authorization: String,
        @Query("specialization") specialization: String,
        @Query("name") query: String,
        @Query("distance") distance: Double,
        @Query("originLat") lat: Double,
        @Query("originLon") long: Double
    ): Observable<ArrayList<HealthService>>

    @GET("api/health-services/hospitals/{id}")
    fun hospitalDetail(
        @Path("id") id: Int,
        @Query("originLat") lat: Double,
        @Query("originLon") long: Double
    ): Observable<HealthService>

    @GET("api/health-services/doctors/{id}")
    fun doctorDetail(
        @Path("id") id: Int,
        @Query("originLat") lat: Double,
        @Query("originLon") long: Double
    ): Observable<HealthService>

    @GET("api/authorizations")
    fun authorizations(
        @Header("Authorization") authorization: String
    ): Observable<ArrayList<Authorization>>

    @FormUrlEncoded
    @POST("api/authorizations")
    fun createAuthorization(
        @Header("Authorization") authorization: String,
        @Field("title") title: String,
        @Field("created_by") from: String,
        @Field("created_for") to: String,
        @Field("type") type: Int
    ): Observable<AuthResponse>

    @GET("api/users/{dni}/family-group")
    fun getFamilyGroup(
        @Path("dni") dni: String
    ): Observable<ArrayList<FamilyUser>>

    @GET("api/authorizations/types")
    fun getTypes(): Observable<ArrayList<AuthorizationType>>
}