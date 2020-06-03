package ru.alexbox.weatherapp.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "roomCity")
    public String roomCity;

    @ColumnInfo(name = "roomTemperature")
    public String roomTemperature;
}
