package com.example.tpandroid.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\bJ\u000e\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fJ\u0015\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$\u00a2\u0006\u0002\u0010%J\u000e\u0010&\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\bJ\u0006\u0010\'\u001a\u00020\u0018R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u00148F\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006("}, d2 = {"Lcom/example/tpandroid/ui/viewmodel/WeatherViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/example/tpandroid/data/WeatherRepository;", "(Lcom/example/tpandroid/data/WeatherRepository;)V", "_citySuggestions", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/example/tpandroid/data/network/CityResult;", "_favoriteCities", "_weatherState", "Landroidx/lifecycle/MutableLiveData;", "Lcom/example/tpandroid/data/model/WeatherResponse;", "citySuggestions", "Lkotlinx/coroutines/flow/StateFlow;", "getCitySuggestions", "()Lkotlinx/coroutines/flow/StateFlow;", "favoriteCities", "getFavoriteCities", "weatherState", "Landroidx/lifecycle/LiveData;", "getWeatherState", "()Landroidx/lifecycle/LiveData;", "addFavorite", "", "city", "fetchCitySuggestions", "cityName", "", "fetchWeatherByCoordinates", "lat", "", "lon", "getWeatherDescription", "Lcom/example/tpandroid/data/model/WeatherInfo;", "code", "", "(Ljava/lang/Integer;)Lcom/example/tpandroid/data/model/WeatherInfo;", "removeFavorite", "resetWeather", "app_debug"})
public final class WeatherViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.example.tpandroid.data.WeatherRepository repository = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<com.example.tpandroid.data.model.WeatherResponse> _weatherState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.tpandroid.data.network.CityResult>> _citySuggestions = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.tpandroid.data.network.CityResult>> _favoriteCities = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.tpandroid.data.network.CityResult>> favoriteCities = null;
    
    public WeatherViewModel(@org.jetbrains.annotations.NotNull
    com.example.tpandroid.data.WeatherRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<com.example.tpandroid.data.model.WeatherResponse> getWeatherState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.tpandroid.data.network.CityResult>> getCitySuggestions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.tpandroid.data.network.CityResult>> getFavoriteCities() {
        return null;
    }
    
    public final void fetchCitySuggestions(@org.jetbrains.annotations.NotNull
    java.lang.String cityName) {
    }
    
    public final void resetWeather() {
    }
    
    public final void fetchWeatherByCoordinates(double lat, double lon) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.tpandroid.data.model.WeatherInfo getWeatherDescription(@org.jetbrains.annotations.Nullable
    java.lang.Integer code) {
        return null;
    }
    
    public final void addFavorite(@org.jetbrains.annotations.NotNull
    com.example.tpandroid.data.network.CityResult city) {
    }
    
    public final void removeFavorite(@org.jetbrains.annotations.NotNull
    com.example.tpandroid.data.network.CityResult city) {
    }
}