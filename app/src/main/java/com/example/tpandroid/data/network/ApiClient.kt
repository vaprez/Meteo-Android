package com.example.tpandroid.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    private const val BASE_URL = "https://api.open-meteo.com/"
    private const val GEOCODING_BASE_URL = "https://geocoding-api.open-meteo.com/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Retrofit pour l'API de géocodage
    private val retrofitGeocoding: Retrofit = Retrofit.Builder()
        .baseUrl(GEOCODING_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Ajouter l'API de géocodage
    val geocodingApiService: GeocodingApiService by lazy {
        retrofitGeocoding.create(GeocodingApiService::class.java)
    }

    val weatherApiService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}


