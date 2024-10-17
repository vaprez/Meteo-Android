package com.example.tpandroid.data.network

import retrofit2.http.GET
import retrofit2.http.Query

// Modèle de réponse pour l'API de géocodage
data class GeocodingResponse(
    val results: List<CityResult>?
)

data class CityResult(
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val postcodes: List<String>,
    val country : String
)

// Interface pour l'API de géocodage
interface GeocodingApiService {
    @GET("v1/search")
    suspend fun getCityCoordinates(
        @Query("name") cityName: String
    ): GeocodingResponse
}
