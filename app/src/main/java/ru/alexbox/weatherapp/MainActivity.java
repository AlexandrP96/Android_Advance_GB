package ru.alexbox.weatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ru.alexbox.weatherapp.data.WeatherRequest;
import ru.alexbox.weatherapp.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity implements SettingsDialogResult {


    private SettingsDialogBuilderFragment sdbFragment;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initFab();
        initDrawer();
        initThread();
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

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initThread() {
        MainWeatherThread mwt = new MainWeatherThread(new MainWeatherThread.ThreadListener() {
            @Override
            public void onResult(WeatherRequest wr) {
                DisplayInfo(wr);
            }
        });
        mwt.Logic();
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
        initThread();
        FragmentReplacer();
    }

    public void FragmentReplacer() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        HomeFragment hm = new HomeFragment();
        ft.replace(R.id.fragment_gallery, hm);
        ft.commit();
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void DisplayInfo(WeatherRequest wr) {
        TextView City = findViewById(R.id.CityView);
        TextView Temp = findViewById(R.id.TempView);
        TextView Pressure = findViewById(R.id.PressureViewP);
        TextView Wind = findViewById(R.id.WindViewP);
        TextView Humidity = findViewById(R.id.HumidityViewP);
        City.setText(wr.getName());
        Temp.setText(String.format("+ %.0f", wr.getMain().getTemp()));
        Pressure.setText(String.format("%.0f", wr.getMain().getPressure() * 0.750062));
        Wind.setText(String.format("%d", wr.getWind().getSpeed()));
        Humidity.setText(String.format("%d", wr.getMain().getHumidity()));
    }
}
