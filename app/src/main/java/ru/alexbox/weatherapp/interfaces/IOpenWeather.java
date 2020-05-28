package ru.alexbox.weatherapp.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.alexbox.weatherapp.retrofit_data.WeatherRequest;

public interface IOpenWeather {
    @GET("weather")
    Call<WeatherRequest> loadWeather(@Query("q") String cityCountry, @Query("units") String metric, @Query("appid") String keyApi);
}