package com.example.tpandroid.data.network

import com.example.tpandroid.data.model.HourlyData
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.tpandroid.data.model.WeatherResponse

interface WeatherApiService {
    @GET("v1/forecast")
    suspend fun getWeatherForecast(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("hourly") hourly: String = "temperature_2m,weather_code,relative_humidity_2m,apparent_temperature,rain,wind_speed_10m",
        @Query("models") model: String = "meteofrance_seamless"
    ): WeatherResponse
}
