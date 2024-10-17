package com.example.tpandroid.data

import com.example.tpandroid.data.model.HourlyData
import com.example.tpandroid.data.model.WeatherResponse
import com.example.tpandroid.data.network.ApiClient
import com.example.tpandroid.data.network.CityResult
import com.example.tpandroid.data.network.WeatherApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val apiService: WeatherApiService) {

    suspend fun getWeatherForecast(lat: Double, lon: Double): WeatherResponse {
        return apiService.getWeatherForecast(lat, lon)
    }

    // Méthode pour obtenir les suggestions de villes
    suspend fun getCitySuggestions(cityName: String): List<CityResult> {
        return try {
            val geocodingResponse = withContext(Dispatchers.IO) {
                ApiClient.geocodingApiService.getCityCoordinates(cityName)
            }
            geocodingResponse.results ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }



}