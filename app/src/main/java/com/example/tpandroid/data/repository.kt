package com.example.tpandroid.data

import com.example.tpandroid.data.model.FavoriteCity
import com.example.tpandroid.data.model.WeatherEntity
import com.example.tpandroid.data.model.WeatherResponse
import com.example.tpandroid.data.network.ApiClient
import com.example.tpandroid.data.network.CityResult
import com.example.tpandroid.data.network.WeatherApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val apiService: WeatherApiService,
    private val database: AppDatabase
    ) {

    // Récupération de la météo avec gestion du cache
    suspend fun getWeatherForecast(lat: Double, lon: Double): WeatherResponse {
        return try {
            val weatherResponse = apiService.getWeatherForecast(lat, lon)
            withContext(Dispatchers.IO) {
                database.weatherDao().insertWeather(WeatherEntity.from(weatherResponse))
                println("Weather data inserted into the database: $weatherResponse")
            }
            println("weather ${lat} & lon ${lon}")
            val cachedWeather = database.weatherDao().getWeather(lat, lon)
            println(" Weather Retrieved cached data: $cachedWeather")
            weatherResponse
        } catch (e: Exception) {
            val cachedWeather = database.weatherDao().getWeather(lat, lon)
            cachedWeather?.toWeatherResponse() ?: throw Exception("No cached data available")
        }
    }


    // Récupérer les données en cache si elles existent
    suspend fun getCachedWeather(lat: Double, lon: Double): WeatherResponse? {
        return database.weatherDao().getWeather(lat, lon)?.toWeatherResponse()
    }

    suspend fun getCitySuggestions(cityName: String): List<CityResult> {
        return try {
            // Récupération des coordonnées de la ville via l'API de géocodage
            val geocodingResponse = withContext(Dispatchers.IO) {
                ApiClient.geocodingApiService.getCityCoordinates(cityName)
            }

            // Récupérer les résultats de l'API ou une liste vide si null
            val cityResults = geocodingResponse.results ?: emptyList()

            cityResults // Retourne les résultats obtenus depuis l'API
        } catch (e: Exception) {
            throw Exception("nothing")
        }
    }

    suspend fun addFavoriteCity (city : CityResult){
        return try {
            withContext(Dispatchers.IO){
                database.weatherDao().insertCity(FavoriteCity.from(city))
                println("city inserted into database")
            }
        }catch (e:Exception){
            println("Error during city insertion")
        }
    }

    suspend fun deleteFavoriteCity (city : CityResult){
        return try {
            withContext(Dispatchers.IO){
                database.weatherDao().removeFavoriteCity(FavoriteCity.from(city))
                println("city deleted into database")
            }
        }catch (e:Exception){
            println("Error during city deletion")
        }
    }

    suspend fun getFavoriteCity() : List<CityResult> {
        val favoriteCities =  database.weatherDao().getFavoriteCities()
        return favoriteCities.map { it.toCityResults() }
    }
}