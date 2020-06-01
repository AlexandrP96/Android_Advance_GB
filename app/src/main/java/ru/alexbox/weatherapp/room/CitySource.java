package ru.alexbox.weatherapp.room;

import java.util.List;

public class CitySource {

    private final CityDao cityDao;
    private List<City> cities;

    public CitySource(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public List<City> getCities() {
        if (cities == null) {
            LoadCities();
        }
        return cities;
    }

    public void LoadCities() {
        cities = cityDao.getAllCities();
    }

    public long getCountCities() {
        return cityDao.getCountCities();
    }

    public void addCity(City city) {
        cityDao.insertCity(city);
        LoadCities();
    }
}
