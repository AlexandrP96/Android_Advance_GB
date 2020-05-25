package ru.alexbox.weatherapp;

import java.net.MalformedURLException;

public interface SettingsDialogResult {

    void onSettingsResult(String result) throws MalformedURLException;
}
