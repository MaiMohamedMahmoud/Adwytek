package com.marscode.pwn.adwytek.Application;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    public static final String Channel_Id = "serviceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        //call createChannelNotification method
        createChannelNotification();
    }

    //Every Notification should have a channel
    //so create channel Notification
    private void createChannelNotification() {
        // check if version is 26 (oreo) or more
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(Channel_Id, "Service Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }



}
