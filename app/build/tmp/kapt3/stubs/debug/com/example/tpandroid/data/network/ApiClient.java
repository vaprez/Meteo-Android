package com.example.tpandroid.data.network;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0011\u001a\u00020\u00128FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0015\u0010\u000b\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0016"}, d2 = {"Lcom/example/tpandroid/data/network/ApiClient;", "", "()V", "BASE_URL", "", "GEOCODING_BASE_URL", "geocodingApiService", "Lcom/example/tpandroid/data/network/GeocodingApiService;", "getGeocodingApiService", "()Lcom/example/tpandroid/data/network/GeocodingApiService;", "geocodingApiService$delegate", "Lkotlin/Lazy;", "retrofit", "Lretrofit2/Retrofit;", "getRetrofit", "()Lretrofit2/Retrofit;", "retrofitGeocoding", "weatherApiService", "Lcom/example/tpandroid/data/network/WeatherApiService;", "getWeatherApiService", "()Lcom/example/tpandroid/data/network/WeatherApiService;", "weatherApiService$delegate", "app_debug"})
public final class ApiClient {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String BASE_URL = "https://api.open-meteo.com/";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String GEOCODING_BASE_URL = "https://geocoding-api.open-meteo.com/";
    @org.jetbrains.annotations.NotNull
    private static final retrofit2.Retrofit retrofit = null;
    @org.jetbrains.annotations.NotNull
    private static final retrofit2.Retrofit retrofitGeocoding = null;
    @org.jetbrains.annotations.NotNull
    private static final kotlin.Lazy geocodingApiService$delegate = null;
    @org.jetbrains.annotations.NotNull
    private static final kotlin.Lazy weatherApiService$delegate = null;
    @org.jetbrains.annotations.NotNull
    public static final com.example.tpandroid.data.network.ApiClient INSTANCE = null;
    
    private ApiClient() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final retrofit2.Retrofit getRetrofit() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.tpandroid.data.network.GeocodingApiService getGeocodingApiService() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.tpandroid.data.network.WeatherApiService getWeatherApiService() {
        return null;
    }
}