package com.example.tpandroid.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tpandroid.data.network.CityResult
import com.google.gson.Gson


@Entity(tableName = "favorite_cities")
data class FavoriteCity(
    @PrimaryKey val cityName: String,
    val latitude : Double,
    val longitude : Double,
    val country: String,
    val cityData: String
){

    companion object {
        // Méthode pour convertir CityResults en FavoriteCity
        fun from(cityResult: CityResult): FavoriteCity {
            val cityDataJson = Gson().toJson(cityResult) // Conversion de WeatherResponse en JSON
            return FavoriteCity(
                cityName = cityResult.name,
                latitude = cityResult.latitude,
                longitude = cityResult.longitude,
                country = cityResult.country,
                cityData = cityDataJson
            )
        }
    }


    fun toCityResults(): CityResult {
        return Gson().fromJson(this.cityData, CityResult::class.java) // Conversion du JSON en CityResult
    }
}



