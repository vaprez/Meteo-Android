package com.example.tpandroid.data

import androidx.room.*
import com.example.tpandroid.data.model.FavoriteCity
import com.example.tpandroid.data.model.WeatherEntity

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather_cache WHERE latitude = :lat and longitude = :lon LIMIT 1")
    suspend fun getWeather(lat: Double, lon:Double): WeatherEntity?

    @Query("DELETE FROM weather_cache WHERE timestamp < :expirationTime")
    suspend fun clearOldCache(expirationTime: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: FavoriteCity)

    @Query("SELECT * FROM favorite_cities")
    suspend fun getFavoriteCities(cityName: String): List<FavoriteCity>

    @Delete
    suspend fun removeFavoriteCity(city: FavoriteCity)
}
