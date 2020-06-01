package ru.alexbox.weatherapp.room;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {

    private static App instance;
    private HRoomDatabase cityBase;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        cityBase = Room.databaseBuilder(
                getApplicationContext(),
                HRoomDatabase.class,
                "notes_database")
                .allowMainThreadQueries()
                .build();
    }

    public CityDao getCityDao() {
        return cityBase.getCityDao();
    }

}
