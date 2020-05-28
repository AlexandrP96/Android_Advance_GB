package ru.alexbox.weatherapp;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ru.alexbox.weatherapp.interfaces.IOpenWeather;
import ru.alexbox.weatherapp.interfaces.SettingsDialogResult;

@SuppressWarnings("NullableProblems")
public class MainActivity extends AppCompatActivity implements SettingsDialogResult {

    private SettingsDialogBuilderFragment sdbFragment;
    private AppBarConfiguration mAppBarConfiguration;
    private IOpenWeather iOpenWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initFab();
        initDrawer();
        initRetrofit();
    }

    private void initDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void initFab() {
        sdbFragment = new SettingsDialogBuilderFragment();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> sdbFragment.show(getSupportFragmentManager(), "sdbFragment"));
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initRetrofit() {
        Retrofit retrofit = MyApplication.getRetrofitInstance();
        iOpenWeather = retrofit.create(IOpenWeather.class);
        requestRetrofit("moscow", "metric");
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onSettingsResult(String result) {
        TextView textView = findViewById(R.id.TempTypeView);
        textView.setText(R.string.TempF);
        requestRetrofit("moscow", "");
    }

    private void requestRetrofit(String city, String metric) {
        iOpenWeather.loadWeather(city, metric, BuildConfig.WEATHER_API_KEY)
                .enqueue(new Callback<ru.alexbox.weatherapp.retrofit_data.WeatherRequest>() {
                    @Override
                    public void onResponse(Call<ru.alexbox.weatherapp.retrofit_data.WeatherRequest> call, Response<ru.alexbox.weatherapp.retrofit_data.WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            TextView Temp = findViewById(R.id.TempView);
                            float result = response.body().getMain().getTemp();
                            Temp.setText(String.format(Locale.getDefault(), "%.0f", result));
                        }
                    }

                    @Override
                    public void onFailure(Call<ru.alexbox.weatherapp.retrofit_data.WeatherRequest> call, Throwable t) {
                        Toast.makeText(MainActivity.this, R.string.Fail, Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    @SuppressLint({"DefaultLocale", "SetTextI18n"})
//    private void DisplayInfo(WeatherRequest wr) {
//        TextView City = findViewById(R.id.CityView);
//        TextView Temp = findViewById(R.id.TempView);
//        TextView Pressure = findViewById(R.id.PressureViewP);
//        TextView Wind = findViewById(R.id.WindViewP);
//        TextView Humidity = findViewById(R.id.HumidityViewP);
//        City.setText(wr.getName());
//        Temp.setText(String.format("+ %.0f", wr.getMain().getTemp()));
//        Pressure.setText(String.format("%.0f", wr.getMain().getPressure() * 0.750062));
//        Wind.setText(String.format("%d", wr.getWind().getSpeed()));
//        Humidity.setText(String.format("%d", wr.getMain().getHumidity()));
//    }
}
