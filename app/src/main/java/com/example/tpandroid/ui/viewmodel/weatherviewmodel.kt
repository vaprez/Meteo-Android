package com.example.tpandroid.ui.viewmodel

import androidx.lifecycle.*
import com.example.tpandroid.data.WeatherRepository
import com.example.tpandroid.data.model.HourlyData
import com.example.tpandroid.data.model.WeatherResponse
import com.example.tpandroid.data.network.CityResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    // État pour stocker les données météo
    private val _weatherState = MutableLiveData<WeatherResponse?>()
    val weatherState: LiveData<WeatherResponse?> get() = _weatherState

    // État pour stocker les suggestions de villes
    private val _citySuggestions = MutableStateFlow<List<CityResult>>(emptyList())
    val citySuggestions: StateFlow<List<CityResult>> get() = _citySuggestions


    // Fonction pour rechercher les suggestions de villes
    fun fetchCitySuggestions(cityName: String) {
        viewModelScope.launch {
            try {
                val cities = repository.getCitySuggestions(cityName)
                _citySuggestions.value = cities // Mettre à jour les suggestions de villes
            }catch (e:Exception){
                _citySuggestions.value = emptyList()
            }

        }
    }

    // Fonction pour rechercher la météo via latitude et longitude
    fun fetchWeatherByCoordinates(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val weather = repository.getWeatherForecast(lat, lon)
                if (weather != null) {
                    _weatherState.value = weather
                    println("Weather data received: $weather")
                } else {
                    println("No weather data available.")
                }
            }catch (e: Exception){
                println("Error fetching weather data: ${e.message}")
                _weatherState.value = null
            }

        }
    }

    fun getWeatherDescription(code: Int?): String {
        return when (code) {
            0 -> "Ciel dégagé"
            1 -> "Partiellement nuageux"
            2 -> "Nuageux"
            3 -> "Très nuageux"
            45 -> "Brouillard léger"
            48 -> "Brouillard givrant"
            51 -> "Bruine légère"
            53 -> "Bruine modérée"
            55 -> "Bruine dense"
            61 -> "Pluie légère"
            63 -> "Pluie modérée"
            65 -> "Pluie forte"
            71 -> "Neige légère"
            73 -> "Neige modérée"
            75 -> "Neige forte"
            95 -> "Orage léger"
            96 -> "Orage avec grêle"
            else -> "Condition météo inconnue"
        }
    }

}