package com.example.tpandroid.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

@Entity(tableName = "weather_cache")
data class WeatherEntity(
    @PrimaryKey val location: String,
    val latitude : Double,
    val longitude : Double,
    val weatherData: String,
    val timestamp: Long
){
    companion object {
        // Méthode pour convertir WeatherResponse en WeatherEntity
        fun from(weatherResponse: WeatherResponse): WeatherEntity {
            val weatherDataJson = Gson().toJson(weatherResponse) // Conversion de WeatherResponse en JSON
            return WeatherEntity(
                location = "${weatherResponse.latitude},${weatherResponse.longitude}", // Utilisation de la latitude/longitude comme clé
                latitude = weatherResponse.latitude,
                longitude = weatherResponse.longitude,
                weatherData = weatherDataJson,
                timestamp = System.currentTimeMillis()
            )
        }
    }

    // Méthode pour convertir WeatherEntity en WeatherResponse
    fun toWeatherResponse(): WeatherResponse {
        return Gson().fromJson(this.weatherData, WeatherResponse::class.java) // Conversion du JSON en WeatherResponse
    }
}





