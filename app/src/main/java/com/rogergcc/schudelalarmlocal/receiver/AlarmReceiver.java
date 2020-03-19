package com.rogergcc.schudelalarmlocal.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.ContextCompat;

import com.rogergcc.schudelalarmlocal.service.NotificationService;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent2 = new Intent(context, NotificationService.class);
        StringBuilder sb = new StringBuilder();
        sb.append("custom://");
        sb.append(System.currentTimeMillis());
        intent2.setData(Uri.parse(sb.toString()));
        ContextCompat.startForegroundService(context, intent2);
    }
}
