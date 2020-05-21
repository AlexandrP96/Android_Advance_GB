package ru.alexbox.weatherapp;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import ru.alexbox.weatherapp.data.WeatherRequest;


public class MainActivity extends AppCompatActivity implements SettingsDialogResult {


    private SettingsDialogBuilderFragment sdbFragment;
    private AppBarConfiguration mAppBarConfiguration;
    private String unitsT = "&units=metric";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initFab();
        initDrawer();
        Thread(unitsT);
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sdbFragment.show(getSupportFragmentManager(), "sdbFragment");
            }
        });
    }


    @Override
    public void onSettingsResult(String result) {
        TextView textView = findViewById(R.id.TempTypeView);
        textView.setText(R.string.TempF);
        String unitsT = "";
        Thread(unitsT);
    }


    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void Thread(String unitsT) {
        try {
            final URL uri = getUrl("moscow", unitsT);
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {
                    HttpsURLConnection urlC;
                    try {
                        urlC = getHttpsURLConnection(uri);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlC.getInputStream()));
                        String result = getLines(in);
                        Gson gson = new Gson();
                        final WeatherRequest wr = gson.fromJson(result, WeatherRequest.class);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                DisplayInfo(wr);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.N)
                private String getLines(BufferedReader in) {
                    return in.lines().collect(Collectors.joining("\n"));
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private HttpsURLConnection getHttpsURLConnection(URL uri) throws IOException {
        HttpsURLConnection urlC;
        urlC = (HttpsURLConnection) uri.openConnection();
        urlC.setRequestMethod("GET");
        urlC.setReadTimeout(10000);
        return urlC;
    }


    private static URL getUrl(String city, String unitsT) throws MalformedURLException {
        return new URL("https://api.openweathermap.org/data/2.5/weather?q="
                + city + unitsT + "&appid=" + BuildConfig.WEATHER_API_KEY);
    }


    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void DisplayInfo(WeatherRequest wr) {
        TextView City = findViewById(R.id.CityView);
        TextView Temp = findViewById(R.id.TempView);
        TextView Pressure = findViewById(R.id.PressureViewP);
        TextView Wind = findViewById(R.id.WindViewP);
        TextView Humidity = findViewById(R.id.HumidityViewP);
        City.setText(wr.getName());
        Temp.setText(String.format("%f1", wr.getMain().getTemp()));
        Pressure.setText(String.format("%d", wr.getMain().getPressure()));
        Wind.setText(String.format("%d", wr.getWind().getSpeed()));
        Humidity.setText(String.format("%d", wr.getMain().getHumidity()));
    }
}
