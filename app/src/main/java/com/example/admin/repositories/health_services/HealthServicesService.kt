package com.example.admin.repositories.health_services

import com.example.admin.models.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @JvmSuppressWildcards
    @Multipart
    @POST("api/authorizations")
    fun createAuthorization(
        @Header("Authorization") authorization: String,
        @PartMap params: Map<String, RequestBody>
        //@Part("title") title: RequestBody,
        //@Part("created_by") from: RequestBody,
        //@Part("created_for") to: RequestBody,
        //@Part("type") type: RequestBody,
        //@Part("photo") photo: MultipartBody.Part
    ): Observable<AuthResponse>

    @GET("api/users/{dni}/family-group")
    fun getFamilyGroup(
        @Path("dni") dni: String
    ): Observable<ArrayList<FamilyUser>>

    @GET("api/authorizations/types")
    fun getTypes(): Observable<ArrayList<AuthorizationType>>
}