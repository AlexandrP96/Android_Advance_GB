package ru.alexbox.weatherapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

import retrofit2.Response;
import ru.alexbox.weatherapp.broadcastreceiver.AirplaneReceiver;
import ru.alexbox.weatherapp.broadcastreceiver.BatteryReceiver;
import ru.alexbox.weatherapp.dialog.SettingsDialogBuilderFragment;
import ru.alexbox.weatherapp.dialog.SettingsDialogResult;
import ru.alexbox.weatherapp.parcel.Parcel;
import ru.alexbox.weatherapp.retrofit.Retrofit;
import ru.alexbox.weatherapp.retrofit_data.WeatherRequest;

import static ru.alexbox.weatherapp.broadcastreceiver.AirplaneReceiver.CHANNEL_ID;
import static ru.alexbox.weatherapp.parcel.Constants.PARCEL;

public class MainActivity extends AppCompatActivity implements SettingsDialogResult {

    public static final String CHANNEL_NAME = "name";
    private SettingsDialogBuilderFragment sdbFragment;
    private AppBarConfiguration mAppBarConfiguration;
    private BroadcastReceiver airplaneReceiver;
    private BroadcastReceiver batteryReceiver;
    private TextView City;
    private TextView Temperature;

    // Убрал savedState
    // Проверка подключения!
    // Проверка батарейки!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AirplaneCheck();
        BatteryCheck();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
        initRetrofit();
        initToolbar();
        initFab();
        initDrawer();
        initChannel();
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

    private void initView() {
        City = findViewById(R.id.CityView);
        Temperature = findViewById(R.id.TempView);
    }

    private void initChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void initRetrofit() {
        new Retrofit(this::DisplayInfo);
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

    private void AirplaneCheck() {
        airplaneReceiver = new AirplaneReceiver();
        registerReceiver(airplaneReceiver, new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
    }

    private void BatteryCheck() {
        batteryReceiver = new BatteryReceiver();
        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));
    }

    @Override
    public void onSettingsResult(String result) {
        TextView textView = findViewById(R.id.TempTypeView);
        textView.setText(R.string.TempF);
        initRetrofit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            Parcel parcel = (Parcel) data.getSerializableExtra(PARCEL);
            if (parcel != null) {
                City.setText(parcel.currentCity);
            } else {
                Toast.makeText(getApplicationContext(), "Parcel Error!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(airplaneReceiver);
        unregisterReceiver(batteryReceiver);
    }

    private void DisplayInfo(Response<WeatherRequest> response) {
        TextView Pressure = findViewById(R.id.PressureViewP);
        TextView Wind = findViewById(R.id.WindViewP);
        TextView Humidity = findViewById(R.id.HumidityViewP);
        TextView Min = findViewById(R.id.MinimumViewPar);
        TextView Max = findViewById(R.id.MaximumViewPar);

        assert response.body() != null;
        float temperature = response.body().getMain().getTemp();
        float minTemp = response.body().getMain().getTemp_min();
        float maxTemp = response.body().getMain().getTemp_max();
        float wind = response.body().getWind().getSpeed();
        float pressure = response.body().getMain().getPressure();
        int humidity = response.body().getMain().getHumidity();
        String city = response.body().getName();

        City.setText(city);
        Temperature.setText(String.format(Locale.getDefault(), "+ %.0f", temperature));
        Wind.setText(String.format(Locale.getDefault(), "%.0f", wind));
        Pressure.setText(String.format(Locale.getDefault(), "%.0f", pressure * 0.750062));
        Humidity.setText(String.format(Locale.getDefault(), "%d", humidity));
        Min.setText(String.format(Locale.getDefault(), "%.0f", minTemp));
        Max.setText(String.format(Locale.getDefault(), "%.0f", maxTemp));
    }
}
