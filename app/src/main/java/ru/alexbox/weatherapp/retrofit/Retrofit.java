package ru.alexbox.weatherapp.retrofit;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.alexbox.weatherapp.BuildConfig;


public abstract class Retrofit {

    private final RetrofitListener retrofitListener;
    private String city = "moscow";

    public Retrofit(RetrofitListener retrofitListener) {
        this.retrofitListener = retrofitListener;
    }

    void Logic() {
        retrofit2.Retrofit retrofit = MyApplication.getRetrofitInstance();
        IOpenWeather iOpenWeather = retrofit.create(IOpenWeather.class);
        iOpenWeather.loadWeather(city, "metric", BuildConfig.WEATHER_API_KEY)
                .enqueue(new Callback<ru.alexbox.weatherapp.retrofit_data.WeatherRequest>() {
                    @Override
                    public void onResponse(Call<ru.alexbox.weatherapp.retrofit_data.WeatherRequest> call,
                                           Response<ru.alexbox.weatherapp.retrofit_data.WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            Float temp = response.body().getMain().getTemp();
                            retrofitListener.onResult(temp);
                        }
                    }

                    @Override
                    public void onFailure(Call<ru.alexbox.weatherapp.retrofit_data.WeatherRequest> call, Throwable t) {
                        Log.println(Log.ERROR, "Retrofit", "onFailure");
                    }
                });

    }

    public abstract static class RetrofitListener {
        protected abstract void onResult(Float response);
    }
}
