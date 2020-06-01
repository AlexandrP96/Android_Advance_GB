package ru.alexbox.weatherapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {City.class}, version = 1)
public abstract class HRoomDatabase extends RoomDatabase {
    public abstract CityDao getCityDao();
}
