package ru.alexbox.weatherapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class SettingsDialogBuilderFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final SettingsDialogResult sdRes = (SettingsDialogResult) requireActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity()).setTitle(R.string.settings_WT)
                .setPositiveButton(R.string.Button_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        sdRes.onSettingsResult(getString(R.string.Button_yes));
                    }
                })
                .setCancelable(true)
                .setNegativeButton(R.string.Button_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                        sdRes.onSettingsResult(getString(R.string.Button_no));
                    }
                })
                .setMessage(R.string.settings_WTT);
        return builder.create();
    }
}