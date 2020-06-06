package ru.alexbox.weatherapp.broadcastreceiver;

import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ru.alexbox.weatherapp.R;


public class FirebaseMessageService extends FirebaseMessagingService {

    public static final String CHANNEL_ID = "3";
    private int msgID = 0;

    public FirebaseMessageService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        if (title == null) {
            title = "Firebase push";
        }

        String message = remoteMessage.getNotification().getBody();
        NotificationCompat.Builder builder = getBuilder(title, message);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.notify(msgID, builder.build());

        super.onMessageReceived(remoteMessage);
    }

    private NotificationCompat.Builder getBuilder(String title, String message) {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.cloud)
                    .setContentText(message)
                    .setContentTitle(title);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }
}
