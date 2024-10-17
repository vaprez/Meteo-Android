package com.example.tpandroid.data.model

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val hourly: HourlyData
)

data class HourlyData(
    val temperature_2m: List<Double?>,
    val wind_speed_10m: List<Double?>,
    val weather_code: List<Int?>,
    val time: List<String>

)


