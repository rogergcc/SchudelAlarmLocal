package com.rogergcc.schudelalarmlocal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.rogergcc.schudelalarmlocal.receiver.AlarmReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Utils {
    private static final int DAYS_IN_A_WEEK = 7;
    private static final int DAYS_WITH_ALARM = 365;
    public static String GET_NOTIFICATIONS_TIME = "tvNotificactionDateTime";
    public static String GET_NOTIFICATIONS_DATE = "notificationsDate";
    public static final String TODAY = "today";
    public static final String TODAYS_INT = "todaysInt";
    static SharedPreferences prefs;

    public static void setNotificationsForNextWeek(Context context) {
        prefs = context.getSharedPreferences(context.getString(R.string.app_name), 0);


        //deleteAllAlarms(context);
        deleteAlarm(context);
        Calendar instance = Calendar.getInstance();
        Calendar instance2 = Calendar.getInstance();
        int i = instance2.get(Calendar.DAY_OF_YEAR);
//        for (int i2 = 0; i2 < DAYS_WITH_ALARM; i2++) {
//            String prefsString = prefs.getString(GET_NOTIFICATIONS_TIME, "12:00");
//            instance.set(Calendar.DAY_OF_YEAR, (i + i2) % DAYS_WITH_ALARM);
//            String str = ":";
//            instance.set(Calendar.HOUR_OF_DAY, Integer.parseInt(prefsString.split(str)[0]));
//            instance.set(Calendar.MINUTE, Integer.parseInt(prefsString.split(str)[1]));
//            instance.set(Calendar.SECOND, 0);
//            if (instance.getTimeInMillis() > instance2.getTimeInMillis()) {
//                setAlarm(i2, instance.getTimeInMillis(), context);
//            }
//        }

        //region Una sola Alarma Fecha Exacta

        String prefsStringTime = prefs.getString(GET_NOTIFICATIONS_TIME, "12:00:00");
        String prefsStringDate = prefs.getString(GET_NOTIFICATIONS_DATE, "03-04-2020");
        String str = ":";
        String strD = "-";


        int day = Integer.parseInt(prefsStringDate.split(strD)[0]);
        int month = Integer.parseInt(prefsStringDate.split(strD)[1]);
        int year = Integer.parseInt(prefsStringDate.split(strD)[2]);


        instance.set(Calendar.YEAR, year);
        instance.set(Calendar.MONTH, month);
        instance.set(Calendar.DAY_OF_MONTH, day);

        int hday = Integer.parseInt(prefsStringTime.split(str)[0]);
        int minuto = Integer.parseInt(prefsStringTime.split(str)[1]);

        instance.set(Calendar.HOUR_OF_DAY, hday);
        instance.set(Calendar.MINUTE, minuto);
        instance.set(Calendar.SECOND, 0);
//        if (instance.getTimeInMillis() > instance2.getTimeInMillis()) {
//            setAlarm(0, instance.getTimeInMillis(), context);
//        }


        if (instance.getTimeInMillis() > instance2.getTimeInMillis()) {
            setAlarm(0, instance.getTimeInMillis(), context);
        }

        //endregion
    }

    private static void deleteAllAlarms(Context context) {
        Log.d("CTO", "DELETING ALARMS");
        for (int i = 0; i < DAYS_WITH_ALARM; i++) {
//            ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM))
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE))
                    .cancel(PendingIntent.getBroadcast(context, i, new Intent(context, AlarmReceiver.class), PendingIntent.FLAG_ONE_SHOT));
        }
    }

    private static void deleteAlarm(Context context) {
        Log.d("CTO", "DELETING ALARM ---");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        //requestCode must be incremental to create multiple reminders
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_ONE_SHOT);

//        ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE))
//                .cancel(pendingIntent);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private static void setAlarm(int i, Long l, Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append("SET ALARMS: ");
        sb.append(transformToDate(l));
        Log.d("VICKS", sb.toString());
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_ONE_SHOT);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("custom://");
        sb2.append(System.currentTimeMillis());
        intent.setData(Uri.parse(sb2.toString()));
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, l, broadcast);
        }
    }

    public static String transformToDate(long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // mes 01 dia 01
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d HH:mm:ss"); // mes 1 dia 1
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(j);
    }

    //return The date with the first letter moonth uppercase
    // parameter datetime format "dd MMMM yyyy HH:mm"

    public static String getDateTimeMonthUppercase(String datetime) {
        String datereturn = datetime;
        String[] strArr = datereturn.split(" ");
        String primeraLetra = strArr[1].substring(0, 1);

        String mayuscula = primeraLetra.toUpperCase();
        String demasLetras = strArr[1].substring(1, strArr[1].length());
        String mesMayus = mayuscula + demasLetras;

        datereturn = String.format("%s %s %s %s", strArr[0], mesMayus, strArr[2], strArr[3]);

        return datereturn;
    }
}
