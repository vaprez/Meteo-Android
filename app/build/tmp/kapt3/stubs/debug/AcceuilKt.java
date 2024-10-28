
@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007\u001a \u0010\n\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0007\u00a8\u0006\u000f"}, d2 = {"Acceuil", "", "viewModel", "Lcom/example/tpandroid/ui/viewmodel/WeatherViewModel;", "navController", "Landroidx/navigation/NavController;", "cityName", "", "city", "Lcom/example/tpandroid/data/network/CityResult;", "getTemperatureForToday", "Lkotlin/Pair;", "", "weather", "Lcom/example/tpandroid/data/model/WeatherResponse;", "app_debug"})
public final class AcceuilKt {
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, com.google.accompanist.pager.ExperimentalPagerApi.class})
    @androidx.compose.runtime.Composable
    public static final void Acceuil(@org.jetbrains.annotations.NotNull
    com.example.tpandroid.ui.viewmodel.WeatherViewModel viewModel, @org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    java.lang.String cityName, @org.jetbrains.annotations.NotNull
    com.example.tpandroid.data.network.CityResult city) {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    @org.jetbrains.annotations.NotNull
    public static final kotlin.Pair<java.lang.Double, java.lang.Double> getTemperatureForToday(@org.jetbrains.annotations.NotNull
    com.example.tpandroid.data.model.WeatherResponse weather) {
        return null;
    }
}