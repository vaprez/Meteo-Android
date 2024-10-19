package com.example.tpandroid.ui.viewmodel

import androidx.lifecycle.*
import com.example.tpandroid.R
import com.example.tpandroid.data.WeatherRepository
import com.example.tpandroid.data.model.HourlyData
import com.example.tpandroid.data.model.WeatherInfo
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


    // Liste réactive des villes favorites
    private val _favoriteCities = MutableStateFlow<List<CityResult>>(emptyList())
    val favoriteCities: StateFlow<List<CityResult>> = _favoriteCities


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

    fun getWeatherDescription(code: Int?): WeatherInfo {
        return when (code) {
            0 -> WeatherInfo("Ciel dégagé", R.drawable.ciel_clair) // Image for clear sky
            1 -> WeatherInfo("Partiellement nuageux", R.drawable.partiellement_nuageux)
            2 -> WeatherInfo("Nuageux", R.drawable.nuageux)
            3 -> WeatherInfo("Très nuageux", R.drawable.nuageux)
            45 -> WeatherInfo("Brouillard léger", R.drawable.brouillard)
            48 -> WeatherInfo("Brouillard givrant", R.drawable.trop_brouillard)
            51 -> WeatherInfo("Bruine légère", R.drawable.bruine)
            53 -> WeatherInfo("Bruine modérée", R.drawable.bruine)
            55 -> WeatherInfo("Bruine dense", R.drawable.bruine)
            61 -> WeatherInfo("Pluie légère", R.drawable.pluie_legere)
            63 -> WeatherInfo("Pluie modérée", R.drawable.pluie_abondante)
            65 -> WeatherInfo("Pluie forte", R.drawable.pluie_abondante)
            71 -> WeatherInfo("Neige légère", R.drawable.flocon_de_neige)
            73 -> WeatherInfo("Neige modérée", R.drawable.flocon_de_neige)
            75 -> WeatherInfo("Neige forte", R.drawable.boule_de_neige)
            95 -> WeatherInfo("Orage léger", R.drawable.orage)
            96 -> WeatherInfo("Orage avec grêle", R.drawable.averse_de_grele)
            else -> WeatherInfo("Condition météo inconnue", R.drawable.inconnu)
        }
    }


    // Ajouter une ville aux favoris
    fun addFavorite(city: CityResult) {
        _favoriteCities.value = _favoriteCities.value + city
    }

    // Supprimer une ville des favoris
    fun removeFavorite(city: CityResult) {
        _favoriteCities.value = _favoriteCities.value.filter { it != city }
    }


}