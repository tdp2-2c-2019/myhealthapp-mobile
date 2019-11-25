package com.example.admin.repositories.health_services

import com.example.admin.models.*
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
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
        type: Int,
        file: File
    ): Observable<AuthResponse> {
        val photo = RequestBody.create(MediaType.parse("image/*"), file)
        val titlePart = RequestBody.create(MediaType.parse("text/plain"), title)
        val fromPart = RequestBody.create(MediaType.parse("text/plain"), from)
        val toPart = RequestBody.create(MediaType.parse("text/plain"), to)
        val typePart = RequestBody.create(MediaType.parse("text/plain"), type.toString())
        //val filePart = MultipartBody.Part.createFormData("file", file.name, photo)

        val map = HashMap<String, RequestBody>()
        map["title"] = titlePart
        map["created_by"] = fromPart
        map["created_for"] = toPart
        map["type"] = typePart
        //map.put("photo", filePart)
        map["photo\"; filename=\"image.jpeg\""] = photo

        return healthServicesService.createAuthorization(token, map)
    }

    fun getFamilyGroup(dni: String): Observable<ArrayList<FamilyUser>> {
        return healthServicesService.getFamilyGroup(dni)
    }

    fun getTypes(): Observable<ArrayList<AuthorizationType>> {
        return healthServicesService.getTypes()
    }

}
