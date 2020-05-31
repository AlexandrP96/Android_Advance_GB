package ru.alexbox.weatherapp;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.alexbox.weatherapp.interfaces.IOpenWeather;
import ru.alexbox.weatherapp.retrofit_data.WeatherRequest;

public class Retrofit {

    private final RetrofitListener retrofitListener;
    private IOpenWeather iOpenWeather;

    public Retrofit(RetrofitListener retrofitListener) {
        this.retrofitListener = retrofitListener;
    }

    public interface RetrofitListener {
        void onResult(Response<WeatherRequest> wr);
    }

    void RetrofitLogic() {
        retrofit2.Retrofit retrofit = MyApplication.getRetrofitInstance();
        iOpenWeather = retrofit.create(IOpenWeather.class);
        iOpenWeather.loadWeather("moscow", "metric", BuildConfig.WEATHER_API_KEY)
                .enqueue(new Callback<ru.alexbox.weatherapp.retrofit_data.WeatherRequest>() {
                    @Override
                    public void onResponse(Call<ru.alexbox.weatherapp.retrofit_data.WeatherRequest> call,
                                           Response<ru.alexbox.weatherapp.retrofit_data.WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            retrofitListener.onResult(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<ru.alexbox.weatherapp.retrofit_data.WeatherRequest> call, Throwable t) {
                        Log.println(Log.ERROR, "Retrofit", "onFailure");
                    }
                });
    }
}
