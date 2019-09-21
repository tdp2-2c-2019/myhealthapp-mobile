package com.example.admin.utils

import org.json.JSONObject
import retrofit2.HttpException

class Utils {

    companion object {
        fun extractError(error: Throwable): String {
            return try {
                val jObjError = JSONObject((error as HttpException).response().errorBody()?.string())
                jObjError.getString("error")
            } catch (e: Exception) {
                "Ocurrió un error de conexión"
            }
        }
    }
}