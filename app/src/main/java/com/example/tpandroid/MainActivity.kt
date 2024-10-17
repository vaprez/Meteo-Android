package com.example.tpandroid

import Acceuil
import HomePage
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tpandroid.data.WeatherRepository
import com.example.tpandroid.data.network.ApiClient
import com.example.tpandroid.data.network.CityResult
import com.example.tpandroid.data.network.WeatherApiService
import com.example.tpandroid.ui.theme.TpAndroidTheme
import com.example.tpandroid.ui.viewmodel.WeatherViewModel
import com.example.tpandroid.ui.viewmodel.WeatherViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: WeatherViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiService = ApiClient.weatherApiService
        val repository = WeatherRepository(apiService)
        val viewModelFactory = WeatherViewModelFactory(repository)
        val weatherViewModel: WeatherViewModel by viewModels { viewModelFactory }



        // Définir le contenu de l'interface utilisateur
        setContent {
            TpAndroidTheme {
                // Remember the NavController
                val navController = rememberNavController()
                val selectedCityName = remember { mutableStateOf("") }

                // Define the NavHost with routes
                NavHost(navController = navController, startDestination = "homePage") {
                    composable("homePage") {
                        // État pour stocker la ville sélectionnée
                        val selectedCity = remember { mutableStateOf<CityResult?>(null) }
                        // Appeler HomePage et passer le ViewModel
                        HomePage(
                            onCitySelected = { city ->
                                // Stocker le nom de la ville sélectionnée
                                selectedCity.value = city
                                selectedCityName.value = city.name
                            },
                            onCityValidated = {
                                // Lorsque l'utilisateur valide la ville sélectionnée
                                selectedCity.value?.let { city ->
                                    weatherViewModel.fetchWeatherByCoordinates(city.latitude, city.longitude)
                                }
                            },
                            onLocationSearch = { location ->
                                location?.let {
                                    weatherViewModel.fetchWeatherByCoordinates(it.latitude, it.longitude)

                                }
                            },
                            viewModel = weatherViewModel,
                            navController = navController
                        )
                    }
                    // Define WeatherDetailPage route
                    composable("Acceuil") {
                        Acceuil(viewModel = weatherViewModel,navController=navController,cityName = selectedCityName.value)
                    }
                }

            }
        }
    }
}
