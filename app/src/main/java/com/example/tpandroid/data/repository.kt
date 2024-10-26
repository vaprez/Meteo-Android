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
    private val apiService: WeatherApiService
//    private val database: AppDatabase
    ) {


    suspend fun getWeatherForecast(lat: Double, lon: Double): WeatherResponse {
        return try {
            // Essayez de récupérer les données de l'API
            val weatherResponse = apiService.getWeatherForecast(lat, lon)

//            // Si la réponse est valide, stockez-la dans la base de données
//            withContext(Dispatchers.IO) {
//                database.weatherDao().insertWeather(WeatherEntity.from(weatherResponse))
//            }

            weatherResponse
        } catch (e: Exception) {

            val weatherResponse = apiService.getWeatherForecast(lat, lon)

            weatherResponse
//            // En cas d'erreur (par exemple, hors connexion), récupérez les données depuis la base locale
//            val cachedWeather = database.weatherDao().getWeather(lon,lat)
//            cachedWeather?.toWeatherResponse() ?: throw Exception("No cached data available")
        }
    }


    suspend fun getCitySuggestions(cityName: String): List<CityResult> {
        return try {
            // Récupération des coordonnées de la ville via l'API de géocodage
            val geocodingResponse = withContext(Dispatchers.IO) {
                ApiClient.geocodingApiService.getCityCoordinates(cityName)
            }

            // Récupérer les résultats de l'API ou une liste vide si null
            val cityResults = geocodingResponse.results ?: emptyList()

//            // Sauvegarde des résultats dans la base de données pour le mode hors-ligne
//            if (cityResults.isNotEmpty()) {
//                withContext(Dispatchers.IO) {
//                    cityResults.forEach { city ->
//                        // Sauvegarder chaque ville dans la base de données locale
//                        database.weatherDao().insertCity(FavoriteCity.from(city))
//                    }
//                }
//            }

            cityResults // Retourne les résultats obtenus depuis l'API
        } catch (e: Exception) {
            // Récupération des coordonnées de la ville via l'API de géocodage
            val geocodingResponse = withContext(Dispatchers.IO) {
                ApiClient.geocodingApiService.getCityCoordinates(cityName)
            }
            val cityResults = geocodingResponse.results ?: emptyList()

            cityResults

            // En cas d'erreur ou en mode hors connexion, récupérer les suggestions depuis la base locale
//            val cachedCities = database.weatherDao().getFavoriteCities(cityName)
//            cachedCities.map { it.toCityResults() } ?: emptyList()
        }
    }



}