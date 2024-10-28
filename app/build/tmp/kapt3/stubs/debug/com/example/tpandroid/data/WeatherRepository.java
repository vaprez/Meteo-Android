package com.example.tpandroid.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J#\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fJ\u001f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J!\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0014"}, d2 = {"Lcom/example/tpandroid/data/WeatherRepository;", "", "apiService", "Lcom/example/tpandroid/data/network/WeatherApiService;", "database", "Lcom/example/tpandroid/data/AppDatabase;", "(Lcom/example/tpandroid/data/network/WeatherApiService;Lcom/example/tpandroid/data/AppDatabase;)V", "getCachedWeather", "Lcom/example/tpandroid/data/model/WeatherResponse;", "lat", "", "lon", "(DDLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCitySuggestions", "", "Lcom/example/tpandroid/data/network/CityResult;", "cityName", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWeatherForecast", "app_debug"})
public final class WeatherRepository {
    @org.jetbrains.annotations.NotNull
    private final com.example.tpandroid.data.network.WeatherApiService apiService = null;
    @org.jetbrains.annotations.NotNull
    private final com.example.tpandroid.data.AppDatabase database = null;
    
    public WeatherRepository(@org.jetbrains.annotations.NotNull
    com.example.tpandroid.data.network.WeatherApiService apiService, @org.jetbrains.annotations.NotNull
    com.example.tpandroid.data.AppDatabase database) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getWeatherForecast(double lat, double lon, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.tpandroid.data.model.WeatherResponse> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getCachedWeather(double lat, double lon, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.tpandroid.data.model.WeatherResponse> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getCitySuggestions(@org.jetbrains.annotations.NotNull
    java.lang.String cityName, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.tpandroid.data.network.CityResult>> $completion) {
        return null;
    }
}