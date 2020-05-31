package ru.alexbox.weatherapp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.net.MalformedURLException;

import ru.alexbox.weatherapp.R;

public class SettingsDialogBuilderFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final SettingsDialogResult sdRes = (SettingsDialogResult) requireActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity()).setTitle(R.string.settings_WT)
                .setPositiveButton(R.string.Button_yes, (dialog, which) -> {
                    dismiss();
                    try {
                        sdRes.onSettingsResult(getString(R.string.Button_yes));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                })
                .setCancelable(true)
                .setNegativeButton(R.string.Button_no, (dialog, which) -> {
                    dismiss();
                    try {
                        sdRes.onSettingsResult(getString(R.string.Button_no));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                })
                .setMessage(R.string.settings_WTT);
        return builder.create();
    }
}
