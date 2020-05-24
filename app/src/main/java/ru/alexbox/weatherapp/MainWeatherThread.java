package ru.alexbox.weatherapp;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import ru.alexbox.weatherapp.data.WeatherRequest;

class MainWeatherThread {

    private final ThreadListener threadListener;
    private String unitsT = "&units=metric";
    private String city = "moscow";

    MainWeatherThread(ThreadListener threadListener) {
        this.threadListener = threadListener;
    }

    public interface ThreadListener {
        void onResult(WeatherRequest wr);
    }

    void Logic() throws MalformedURLException {
        final URL uri = getUrl(city, unitsT);
        final Handler handler = new Handler(Looper.myLooper());
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                HttpsURLConnection urlC = null;
                try {
                    urlC = getHttpsURLConnection(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader in = null;
                try {
                    if (urlC != null) {
                        in = new BufferedReader(new InputStreamReader(urlC.getInputStream()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String result = getLines(in);
                Gson gson = new Gson();
                final WeatherRequest wr = gson.fromJson(result, WeatherRequest.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        threadListener.onResult(wr);
                    }
                });
            }

        }).start();
    }

    private HttpsURLConnection getHttpsURLConnection(URL uri) throws IOException {
        HttpsURLConnection urlC;
        urlC = (HttpsURLConnection) uri.openConnection();
        urlC.setRequestMethod("GET");
        urlC.setReadTimeout(10000);
        return urlC;
    }

    private static URL getUrl(String city, String unitsT) throws MalformedURLException {
        return new URL("https://api.openweathermap.org/data/2.5/weather?q="
                + city + unitsT + "&appid=" + BuildConfig.WEATHER_API_KEY);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }
}
