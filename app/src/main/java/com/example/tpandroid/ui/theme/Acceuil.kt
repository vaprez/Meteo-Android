import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ArrowBack // Importer l'icône de retour
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tpandroid.data.model.WeatherResponse
import com.example.tpandroid.ui.viewmodel.WeatherViewModel
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun Acceuil(viewModel: WeatherViewModel, navController: NavController,cityName:String) {
    // Use collectAsState to observe the StateFlow from ViewModel
    val weatherResponse by viewModel.weatherState.observeAsState()

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            // TopAppBar avec un bouton de retour
            TopAppBar(
                title = { Text(text = "Météo") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display current weather if available
            weatherResponse?.let { weather ->
                println("WeatherResponse: $weatherResponse")
                println("test:${weather.hourly.weather_code}")

                if (weather.hourly.temperature_2m.isNotEmpty() &&
                    weather.hourly.wind_speed_10m.isNotEmpty() &&
                    weather.hourly.weather_code.isNotEmpty() &&
                    weather.hourly.time.isNotEmpty()

                ) {

                    // Get current hour
                    val currentHour = LocalDateTime.now().hour

                    // Limit to the next 20 hours from the current hour
                    val next20Hours = weather.hourly.temperature_2m.drop(currentHour).take(20)
                    val windSpeedsNext20Hours = weather.hourly.wind_speed_10m.drop(currentHour).take(20)
                    val timeNext20Hours = weather.hourly.time.drop(currentHour).take(20)
                    val weatherCodesNext20Hours = weather.hourly.weather_code.drop(currentHour).take(20)

                    val zoneParis = ZoneId.of("Europe/Paris")
                    val formatter = DateTimeFormatter.ofPattern("HH:mm")
                    val currentTemperature = next20Hours[0]
                    val currentTime = (ZonedDateTime.now(zoneParis)).format(formatter)
                    val (minTemperature, maxTemperature) = getTemperatureForToday(weather)
                    val currentweatherDescription = viewModel.getWeatherDescription(weatherCodesNext20Hours[0])



                    // Main card with current time, temperature, and wind speed
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Box(
                            contentAlignment = Alignment.TopEnd
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "$currentTime",
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Light,
                                    textAlign = TextAlign.Center
                                )

                                Text(
                                    text = "${currentTemperature}°C",
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )

                                // Display weather condition
                                Text(
                                    text = currentweatherDescription,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Light,
                                    textAlign = TextAlign.Start
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Wind Speed: ${windSpeedsNext20Hours[0]} m/s",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Light
                                )

                                Text(
                                    text = "Min: ${minTemperature}°C | Max: ${maxTemperature}°C",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Light,
                                    textAlign = TextAlign.Center
                                )
                            }

                            // Favorite button in the top-right corner
                            IconButton(onClick = {
                                println("Ville ajoutée aux favoris !")
                            }) {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "Add to Favorites",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    Text(
                        text = cityName,
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // HorizontalPager for the next 20 hours of forecast
                    HorizontalPager(
                        count = next20Hours.size,
                        state = pagerState,
                        modifier = Modifier.fillMaxWidth()
                    ) { page ->
                        // Each card for hourly forecast
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val hourString = timeNext20Hours[page].let {
                                    LocalDateTime.parse(it.toString())
                                        .atZone(ZoneId.of("UTC"))
                                        .withZoneSameInstant(zoneParis) // Convert to Paris time
                                }

                                val displayTime = if (currentHour + page < 24) {
                                    hourString.format(formatter)
                                } else {
                                    hourString.minusHours(24).format(formatter)
                                }

                                // Display time for each forecast hour
                                Text(
                                    text = displayTime,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Light,
                                    textAlign = TextAlign.Start
                                )

                                // Get weather description based on weather code
                                val weatherDescription = viewModel.getWeatherDescription(weatherCodesNext20Hours[page])

                                // Display weather condition
                                Text(
                                    text = weatherDescription,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Light,
                                    textAlign = TextAlign.Start
                                )

                                // Display temperature for each hour
                                Text(
                                    text = "${next20Hours[page]}°C",
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                // Display wind speed
                                Text(
                                    text = "Wind Speed: ${windSpeedsNext20Hours[page]} m/s",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Light
                                )
                            }
                        }
                    }

                    // Horizontal pager indicator
                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                        activeColor = MaterialTheme.colorScheme.primary
                    )
                } else {
                    // Handle empty data case
                    Text(text = "Weather data is not available.", fontSize = 24.sp)
                }
            } ?: run {
                // Display loading state if data is not yet available
                Text(text = "Loading...", fontSize = 24.sp)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTemperatureForToday(weather: WeatherResponse): Pair<Double?, Double?> {

    val today = LocalDate.now()
    val temperaturesForToday = weather.hourly.temperature_2m.filterIndexed { index, _ ->
        index < weather.hourly.time.size // Vérifiez que l'index est valide
        val time = LocalDateTime.parse(weather.hourly.time[index], DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        time.toLocalDate() == today
    } as List<Double>

    return Pair(
        temperaturesForToday.minOrNull(),
        temperaturesForToday.maxOrNull()
    )
}