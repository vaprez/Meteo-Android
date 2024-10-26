import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.transition.CircularPropagation
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tpandroid.data.network.CityResult
import com.example.tpandroid.ui.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

@Composable
fun HomePage(
    onCitySelected: (CityResult) -> Unit, // Callback pour la ville sélectionnée
    onCityValidated: () -> Unit,
    onLocationSearch: (Location?) -> Unit,
    viewModel: WeatherViewModel, // Passer le ViewModel comme paramètre
    navController: NavController
) {
    var position: Location? = null;
    val isLoading = remember { mutableStateOf(false) } // État pour indiquer le chargement
    var city by remember { mutableStateOf(TextFieldValue("")) }
    var showSuggestions by remember { mutableStateOf(false) } // État pour afficher/masquer les suggestions
    var selectedCity by remember { mutableStateOf<CityResult?>(null) } // Variable d'état pour la ville sélectionnée
    val weatherState by viewModel.weatherState.observeAsState() // Obtenir l'état météo
    val citySuggestions by viewModel.citySuggestions.collectAsState() // Obtenir les suggestions de villes
    val favoriteCities by viewModel.favoriteCities.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) } // État pour afficher la boîte de dialogue
    var errorMessage by remember { mutableStateOf("") } // Message d'erreur à afficher
    var titleMessage by remember { mutableStateOf("") } // titre du Message d'erreur à afficher
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
                titleMessage = "Erreur"
                errorMessage = "Permission refusée"
                showErrorDialog = true
                println("Permission refusée")
            }
        }
    )

    // Afficher la boîte de dialogue d'erreur si nécessaire
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text(titleMessage,fontSize = 20.sp) },
            text = { Text(errorMessage, fontSize = 18.sp) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    // Ajoutez une fonction pour réinitialiser l'état
    fun resetState() {
        city = TextFieldValue("")
        selectedCity = null
        showSuggestions = false
        // Si nécessaire, réinitialisez d'autres états ici
    }

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
                            navController.navigate("Acceuil")
                        } else {
                            titleMessage = "Message"
                            errorMessage = "Aucune ville sélectionnée."
                            showErrorDialog = true
                            println("Aucune ville sélectionnée")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Valider la Ville")
                }

                Spacer(modifier = Modifier.height(20.dp))

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
                        Text("Recherchez par géolocalisation",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Light,)

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
                                            if (location != null) {
                                                viewModel.fetchWeatherByCoordinates(location.latitude, location.longitude)
                                                onLocationSearch(location)
                                                navController.navigate("Localisation")
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

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Card pour les villes favorites
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4.dp
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        // Titre de la section
                        Text("Villes Favorites", style = MaterialTheme.typography.h6)

                        // Liste des villes favorites
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .weight(1f)
                        ) {
                            items(favoriteCities) { favoriteCity ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    elevation = 4.dp
                                ) {
                                    // Bouton pour naviguer sur les favoris
                                    IconButton(onClick = {
                                        if(favoriteCity!==null){
                                            onCityValidated()
                                            navController.navigate("Acceuil")
                                        }
                                        else{
                                            println("Erreur de chargement de la ville favorite")
                                        }
                                    }){
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            // Nom de la ville favorite
                                            Text(text = favoriteCity.name)

                                            Icon(
                                                imageVector = Icons.Default.Favorite, // Icône de favori plein pour indiquer que c'est dans les favoris
                                                contentDescription = "Remove from favorites",
                                                tint = MaterialTheme.colors.primary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )

    // Utiliser LaunchedEffect pour naviguer une fois que les données météo sont prêtes
    LaunchedEffect(weatherState) {
        if (weatherState != null) {
            // Terminer le chargement
            isLoading.value = false
            println("affichage")

        } else if (isLoading.value && weatherState == null) {
            // Gérer les erreurs ou l'absence de données météo
            println("ok: Erreur ${weatherState}")
            isLoading.value = false
            titleMessage = "Erreur"
            errorMessage = "Données météo indisponibles. Veuillez réessayer."
            showErrorDialog = true
        }
    }
}

// Fonction pour obtenir la localisation actuelle
fun getCurrentLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onLocationResult: (Location?) -> Unit
) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {

        val locationRequest = LocationRequest.create().apply {
            interval = 10000 // 10 secondes
            fastestInterval = 5000 // 5 secondes
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location = locationResult.lastLocation
                    onLocationResult(location)
                    fusedLocationClient.removeLocationUpdates(this) // Arrêter la mise à jour après avoir reçu une localisation
                }
            },
            Looper.getMainLooper()
        )
    } else {
        println("Permission de localisation non accordée")
        onLocationResult(null)
    }
}

