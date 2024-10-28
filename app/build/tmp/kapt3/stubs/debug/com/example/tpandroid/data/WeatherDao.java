package com.example.tpandroid.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u000b\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ-\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u000eH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J\u0019\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014J\u0019\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J\u0019\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2 = {"Lcom/example/tpandroid/data/WeatherDao;", "", "clearOldCache", "", "expirationTime", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFavoriteCities", "", "Lcom/example/tpandroid/data/model/FavoriteCity;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWeather", "Lcom/example/tpandroid/data/model/WeatherEntity;", "lat", "", "lon", "tolerance", "(DDDLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertCity", "city", "(Lcom/example/tpandroid/data/model/FavoriteCity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertWeather", "weather", "(Lcom/example/tpandroid/data/model/WeatherEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeFavoriteCity", "app_debug"})
@androidx.room.Dao
public abstract interface WeatherDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertWeather(@org.jetbrains.annotations.NotNull
    com.example.tpandroid.data.model.WeatherEntity weather, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM weather_cache \n    WHERE latitude BETWEEN :lat - :tolerance AND :lat + :tolerance \n    AND longitude BETWEEN :lon - :tolerance AND :lon + :tolerance \n    LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getWeather(double lat, double lon, double tolerance, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.tpandroid.data.model.WeatherEntity> $completion);
    
    @androidx.room.Query(value = "DELETE FROM weather_cache WHERE timestamp < :expirationTime")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object clearOldCache(long expirationTime, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertCity(@org.jetbrains.annotations.NotNull
    com.example.tpandroid.data.model.FavoriteCity city, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM favorite_cities")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getFavoriteCities(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.example.tpandroid.data.model.FavoriteCity>> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object removeFavoriteCity(@org.jetbrains.annotations.NotNull
    com.example.tpandroid.data.model.FavoriteCity city, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}