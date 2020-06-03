package ru.alexbox.weatherapp.dialog;

import java.net.MalformedURLException;

public interface SettingsDialogResult {

    void onSettingsResult(String result) throws MalformedURLException;
}
