import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tpandroid.data.network.CityResult
import com.example.tpandroid.ui.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@Composable
fun HomePage(
    onCitySelected: (CityResult) -> Unit, // Callback pour la ville sélectionnée
    onCityValidated: () -> Unit,
    onLocationSearch: (Location?) -> Unit,
    viewModel: WeatherViewModel, // Passer le ViewModel comme paramètre
    navController: NavController
) {
    var city by remember { mutableStateOf(TextFieldValue("")) }
    var showSuggestions by remember { mutableStateOf(false) } // État pour afficher/masquer les suggestions
    var selectedCity by remember { mutableStateOf<CityResult?>(null) } // Variable d'état pour la ville sélectionnée
    val weatherState by viewModel.weatherState.observeAsState() // Obtenir l'état météo
    val citySuggestions by viewModel.citySuggestions.collectAsState() // Obtenir les suggestions de villes

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    // Launcher to request location permission
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                getCurrentLocation(context,fusedLocationClient) { location ->
                    if (location != null) {
                        viewModel.fetchWeatherByCoordinates(location.latitude, location.longitude)
                        onLocationSearch(location)
                    }
                }
            } else {
                // Gérer le cas où la permission n'est pas accordée
                println("Permission refusée")
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Voluptuaria") }, // Nom de l'application dans la TopBar
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Barre de recherche de ville
                OutlinedTextField(
                    value = city,
                    onValueChange = { newValue ->
                        city = newValue
                        showSuggestions = newValue.text.isNotEmpty()
                        if (newValue.text.isNotEmpty()) {
                            viewModel.fetchCitySuggestions(newValue.text) // Rechercher les villes dès qu'on tape
                        }
                    },
                    label = { Text("Search City") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (showSuggestions) {
                    // Afficher les suggestions de villes
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp) // Limiter la hauteur à 200 dp pour rendre la liste scrollable
                    ) {
                        items(citySuggestions) { suggestion ->
                            TextButton(onClick = {
                                city = TextFieldValue(suggestion.name + " (" + suggestion.country + " )") // Remplir le champ de texte avec la ville sélectionnée
                                selectedCity = suggestion // Stocker la ville sélectionnée
                                onCitySelected(suggestion) // Passer la ville sélectionnée
                                showSuggestions = false // Masquer les suggestions après la sélection
                            }) {
                                if (suggestion.postcodes == null) {
                                    Text("${suggestion.name} (${suggestion.country})")
                                } else {
                                    Text("${suggestion.name} (${suggestion.postcodes[0]}, ${suggestion.country})")
                                }
                            }
                        }
                    }
                }

                // Bouton pour valider la sélection de la ville
                Button(
                    onClick = {
                        if (selectedCity != null) {
                            onCityValidated()
                            weatherState?.let { weather ->
                                navController.navigate("Acceuil")
                            } ?: run {
                                println("Météo non disponible, veuillez attendre.")
                            }
                        } else {
                            println("Aucune ville sélectionnée")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Valider la Ville")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Card pour la recherche par géolocalisation
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Ou recherchez par géolocalisation")

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                val permission = Manifest.permission.ACCESS_FINE_LOCATION
                                when {
                                    ContextCompat.checkSelfPermission(
                                        context,
                                        permission
                                    ) == PackageManager.PERMISSION_GRANTED -> {
                                        // Si la permission est déjà accordée, obtenir la localisation
                                        getCurrentLocation(context,fusedLocationClient) { location ->
                                            location?.let {
                                                viewModel.fetchWeatherByCoordinates(it.latitude, it.longitude)
                                                onLocationSearch(location)
                                                navController.navigate("Acceuil")
                                            }
                                        }
                                    }
                                    else -> {
                                        // Demander la permission
                                        locationPermissionLauncher.launch(permission)
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Utiliser ma position actuelle")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    )
}

// Fonction pour obtenir la localisation actuelle
fun getCurrentLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onLocationResult: (Location?) -> Unit
) {
    // Vérifier si la permission est accordée
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {

        // Si la permission est accordée, récupérer la localisation
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                onLocationResult(location)
            }
            .addOnFailureListener {
                println("Impossible d'obtenir la localisation")
                onLocationResult(null)
            }
    } else {
        // Si la permission n'est pas accordée, gérer l'erreur (ou demander la permission)
        println("Permission de localisation non accordée")
        onLocationResult(null)
    }
}

