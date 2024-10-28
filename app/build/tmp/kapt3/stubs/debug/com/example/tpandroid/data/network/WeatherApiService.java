package com.example.tpandroid.data.network;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J9\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u00052\b\b\u0003\u0010\u0007\u001a\u00020\b2\b\b\u0003\u0010\t\u001a\u00020\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000b"}, d2 = {"Lcom/example/tpandroid/data/network/WeatherApiService;", "", "getWeatherForecast", "Lcom/example/tpandroid/data/model/WeatherResponse;", "lat", "", "lon", "hourly", "", "model", "(DDLjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface WeatherApiService {
    
    @retrofit2.http.GET(value = "v1/forecast")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getWeatherForecast(@retrofit2.http.Query(value = "latitude")
    double lat, @retrofit2.http.Query(value = "longitude")
    double lon, @retrofit2.http.Query(value = "hourly")
    @org.jetbrains.annotations.NotNull
    java.lang.String hourly, @retrofit2.http.Query(value = "models")
    @org.jetbrains.annotations.NotNull
    java.lang.String model, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.tpandroid.data.model.WeatherResponse> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}