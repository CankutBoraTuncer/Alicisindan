package com.cankutboratuncer.alicisindan.activities.ui.messaging.firebase;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.ui.messaging.activities.ChatActivity;
import com.cankutboratuncer.alicisindan.activities.utilities.Advertisement;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String productTitle  = remoteMessage.getData().get(Constants.KEY_ADVERTISEMENT_TITLE);
        String productDescription = remoteMessage.getData().get(Constants.KEY_ADVERTISEMENT_DESCRIPTION);
        String userId = remoteMessage.getData().get(Constants.KEY_ADVERTISEMENT_USERID);
        String userName = remoteMessage.getData().get(Constants.KEY_ADVERTISEMENT_USERNAME);
        String productId = remoteMessage.getData().get(Constants.KEY_ADVERTISEMENT_ID);
        String location = remoteMessage.getData().get(Constants.KEY_ADVERTISEMENT_LOCATION);
        String price = remoteMessage.getData().get(Constants.KEY_ADVERTISEMENT_PRICE);
        String image = remoteMessage.getData().get(Constants.KEY_ADVERTISEMENT_IMAGE);
        String brand = remoteMessage.getData().get(Constants.KEY_ADVERTISEMENT_BRAND);
        String type = remoteMessage.getData().get(Constants.KEY_ADVERTISEMENT_TYPE);
        Advertisement advertisement = new Advertisement(productTitle, productDescription, image, price, productId, location,userId, userName, brand, type);

        int notificationId = new Random().nextInt();
        String channelId = "chat_message";

        Intent intent = new Intent(this, ChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Constants.KEY_ADVERTISEMENT, (CharSequence) advertisement);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle(advertisement.getUsername());
        builder.setContentText(remoteMessage.getData().get(Constants.KEY_MESSAGE));
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(
                remoteMessage.getData().get(Constants.KEY_MESSAGE)
        ));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Chat Message";
            String channelDescription = "This notification channel is used for chat message notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId, builder.build());
    }

}