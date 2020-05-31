package ru.alexbox.weatherapp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.alexbox.weatherapp.interfaces.IOpenWeather;
import ru.alexbox.weatherapp.retrofit_data.WeatherRequest;

public class Retrofit {

    private final RetrofitListener retrofitListener;

    public Retrofit(RetrofitListener retrofitListener) {
        this.retrofitListener = retrofitListener;
    }

    public interface RetrofitListener {
        void onResult(Response<WeatherRequest> wr);
    }

    void RetrofitLogic() {
        retrofit2.Retrofit retrofit = MyApplication.getRetrofitInstance();
        IOpenWeather iOpenWeather = retrofit.create(IOpenWeather.class);
        iOpenWeather.loadWeather("moscow", "metric", BuildConfig.WEATHER_API_KEY)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            retrofitListener.onResult(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        //
                    }
                });
    }
}
