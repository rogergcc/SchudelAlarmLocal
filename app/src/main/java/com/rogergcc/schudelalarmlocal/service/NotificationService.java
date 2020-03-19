package com.rogergcc.schudelalarmlocal.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build.VERSION;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Builder;

import com.rogergcc.schudelalarmlocal.MainActivity;
import com.rogergcc.schudelalarmlocal.R;

import java.util.Calendar;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class NotificationService extends IntentService {
    private static int NOTIFICATION_ID = 1;
    Notification notification;
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;

    public NotificationService(String str) {
        super(str);
    }

    public NotificationService() {
        super("SERVICE");
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        String string = getApplicationContext().getString(R.string.app_name);
        Context applicationContext = getApplicationContext();
        String str = Context.NOTIFICATION_SERVICE;
        this.notificationManager = (NotificationManager) applicationContext.getSystemService(str);
        Intent intent2 = new Intent(this, MainActivity.class);
        Resources resources = getResources();
        Uri defaultUri = RingtoneManager.getDefaultUri(4);
        Calendar.getInstance();
        String string2 = getString(R.string.new_notification);
        int i = VERSION.SDK_INT;
        String str2 = NotificationCompat.CATEGORY_SERVICE;
        if (i >= 26) {
            NotificationManager notificationManager2 = (NotificationManager) applicationContext.getSystemService(str);
            if (notificationManager2 == null) {
                notificationManager2 = (NotificationManager) applicationContext.getSystemService(str);
            }
            if (notificationManager2.getNotificationChannel(string) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(string, string, 4);
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notificationManager2.createNotificationChannel(notificationChannel);
            }
            Builder builder = new Builder(applicationContext, string);
            intent2.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            builder.setContentTitle(getString(R.string.app_name))
                    .setCategory(str2)
                    .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_stat_ic_notification))
                    .setSmallIcon(R.drawable.ic_stat_ic_notification)
                    .setContentText(string2).setDefaults(-1).setAutoCancel(true)
                    .setSound(defaultUri).setContentIntent(PendingIntent.getActivity(applicationContext, 0, intent2, 134217728))
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            Notification build = builder.build();
            notificationManager2.notify(0, build);
            startForeground(1, build);
            return;
        }
        this.pendingIntent = PendingIntent.getActivity(applicationContext, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        this.notification = new Builder(this)
                .setContentIntent(this.pendingIntent)
                .setSmallIcon(getNotificationIcon(new Builder(this, string)))
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_stat_ic_notification))
                .setSound(defaultUri)
                .setAutoCancel(true)
                .setContentTitle(getString(R.string.app_name))
                .setCategory(str2)
                .setContentText(string2).build();
        this.notificationManager.notify(NOTIFICATION_ID, this.notification);
    }

    private int getNotificationIcon(Builder builder) {
        if (VERSION.SDK_INT >= 21) {
            builder.setColor(32768);
        }
        return R.mipmap.ic_launcher;
    }
}
