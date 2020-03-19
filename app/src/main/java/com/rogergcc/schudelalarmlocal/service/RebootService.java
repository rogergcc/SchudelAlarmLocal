package com.rogergcc.schudelalarmlocal.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import androidx.core.app.NotificationCompat.Builder;

import com.rogergcc.schudelalarmlocal.Utils;

public class RebootService extends IntentService {
    public RebootService(String str) {
        super(str);
        startForeground(1, new Notification());
    }

    public RebootService() {
        super("RebootService");
    }

    public void onCreate() {
        super.onCreate();
        if (VERSION.SDK_INT >= 26) {
            String str = "my_channel_01";
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(new NotificationChannel(str, "Channel human readable title", 3));
            Builder builder = new Builder(this, str);
            String str2 = "";
            startForeground(1, builder.setContentTitle(str2).setContentText(str2).build());
        }
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        String string = intent.getExtras().getString("caller");
        if (string != null && string.equals("RebootReceiver")) {
            Utils.setNotificationsForNextWeek(this);
        }
    }
}
