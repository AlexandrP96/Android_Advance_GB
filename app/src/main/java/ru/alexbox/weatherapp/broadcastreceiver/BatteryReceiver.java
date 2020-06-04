package ru.alexbox.weatherapp.broadcastreceiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import ru.alexbox.weatherapp.R;

public class BatteryReceiver extends BroadcastReceiver {

    public static final String CHANNEL_ID = "2";
    private int msgID = 2;
    private String name = "Weather App";
    private String message = "Your battery is low";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.cloydys)
                .setContentTitle(name)
                .setContentText(message);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(msgID, builder.build());
    }
}
