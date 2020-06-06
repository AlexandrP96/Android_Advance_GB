package ru.alexbox.weatherapp.broadcastreceiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import ru.alexbox.weatherapp.R;

public class AirplaneReceiver extends BroadcastReceiver {

    public static final String CHANNEL_ID = "1";
    private int msgID = 1;
    private String name = "Weather App";
    private String message = "Airplane mode is on!";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = getBuilder(context);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(msgID, builder.build());
    }

    private NotificationCompat.Builder getBuilder(Context context) {
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.cloud)
                    .setContentTitle(name)
                    .setContentText(message);
    }
}
