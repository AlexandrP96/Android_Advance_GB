package ru.alexbox.weatherapp.broadcastreceiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import ru.alexbox.weatherapp.R;
import ru.alexbox.weatherapp.retrofit.Retrofit;

public class AirplaneReceiver extends BroadcastReceiver {

    private int msgID = 1;
    private String name = "Weather App";
    private String message = "Airplane mode is on!";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Retrofit.CHANNEL_ID)
                .setSmallIcon(R.drawable.cloud)
                .setContentTitle(name)
                .setContentText(message);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(msgID, builder.build());
    }
}
