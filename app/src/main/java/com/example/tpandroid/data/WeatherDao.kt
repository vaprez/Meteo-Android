package com.example.tpandroid.data

import androidx.room.*
import com.example.tpandroid.data.model.FavoriteCity
import com.example.tpandroid.data.model.WeatherEntity

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("""SELECT * FROM weather_cache 
    WHERE latitude BETWEEN :lat - :tolerance AND :lat + :tolerance 
    AND longitude BETWEEN :lon - :tolerance AND :lon + :tolerance 
    LIMIT 1""")
    suspend fun getWeather(lat: Double, lon:Double, tolerance: Double = 0.01): WeatherEntity?

    @Query("DELETE FROM weather_cache WHERE timestamp < :expirationTime")
    suspend fun clearOldCache(expirationTime: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: FavoriteCity)

    // Récupérer toutes les villes favorites
    @Query("SELECT * FROM favorite_cities")
    suspend fun getFavoriteCities(): List<FavoriteCity>

    @Delete
    suspend fun removeFavoriteCity(city: FavoriteCity)
}
