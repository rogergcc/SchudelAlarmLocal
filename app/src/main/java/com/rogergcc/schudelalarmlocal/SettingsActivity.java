package com.rogergcc.schudelalarmlocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    public TextView tvNotificactionDateTime;
    /* access modifiers changed from: private */
    public SharedPreferences sharedPreferences;

    String stringDateSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.sharedPreferences = getSharedPreferences(getString(R.string.app_name), 0);
        this.tvNotificactionDateTime = findViewById(R.id.notifications_date_time);
        String s_date = this.sharedPreferences.getString(Utils.GET_NOTIFICATIONS_DATE,"03-04-2020");
        String s_time = this.sharedPreferences.getString(Utils.GET_NOTIFICATIONS_TIME,"12:00");

        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final SimpleDateFormat sdfToShow = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        String strD = "-";

        int day = Integer.parseInt( s_date.split(strD)[0] );
        int month = Integer.parseInt( s_date.split(strD)[1] );
        int year = Integer.parseInt( s_date.split(strD)[2] );
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);

        String strT = ":";
        int hday = Integer.parseInt(s_time.split(strT)[0]);
        int minuto = Integer.parseInt(s_time.split(strT)[1]);

        cal.set(Calendar.HOUR_OF_DAY, hday);
        cal.set(Calendar.MINUTE, minuto);
        cal.set(Calendar.SECOND, 0);
        sdf.setCalendar(cal);

        stringDateSelected = sdfToShow.format(cal.getTime());
        stringDateSelected = Utils.getDateTimeMonthUppercase(stringDateSelected);
        this.tvNotificactionDateTime.setText(stringDateSelected);

        findViewById(R.id.change_notification).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final Calendar instance = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(SettingsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        instance.set(Calendar.YEAR,year);
                        instance.set(Calendar.MONTH,monthOfYear);
                        instance.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(SettingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker timePicker, int i, int i2) {
                                StringBuilder sb = new StringBuilder();
                                String str = "";
                                sb.append(str);
                                sb.append(i);
                                String sb2 = sb.toString();
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(str);
                                sb3.append(i2);
                                String sb4 = sb3.toString();
                                String str2 = "0";
                                if (i < 10) {
                                    StringBuilder sb5 = new StringBuilder();
                                    sb5.append(str2);
                                    sb5.append(i);
                                    sb2 = sb5.toString();
                                }
                                if (i2 < 10) {
                                    StringBuilder sb6 = new StringBuilder();
                                    sb6.append(str2);
                                    sb6.append(i2);
                                    sb4 = sb6.toString();
                                }
                                TextView tvDateTimeSchedule = SettingsActivity.this.tvNotificactionDateTime;

                                String str3 = ":";
                                SharedPreferences.Editor edit = SettingsActivity.this.sharedPreferences.edit();
                                String preferencesTime = Utils.GET_NOTIFICATIONS_TIME;
                                StringBuilder sb8 = new StringBuilder();
                                sb8.append(sb2);
                                sb8.append(str3);
                                sb8.append(sb4);

                                String day = String.valueOf(instance.get(Calendar.DAY_OF_MONTH));
                                String month = String.valueOf(instance.get(Calendar.MONTH));
                                String year = String.valueOf(instance.get(Calendar.YEAR));
                                String datetime = day+ "-"+month+"-"+year;

                                edit.putString(Utils.GET_NOTIFICATIONS_DATE,datetime);
                                edit.putString(preferencesTime, sb8.toString());

                                instance.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sb2));
                                instance.set(Calendar.MINUTE, Integer.parseInt(sb4));
                                instance.set(Calendar.SECOND, 0);
                                stringDateSelected = sdfToShow.format(instance.getTime());

                                stringDateSelected = Utils.getDateTimeMonthUppercase(stringDateSelected);
                                tvDateTimeSchedule.setText(stringDateSelected);
                                edit.apply();

                                Toast.makeText(SettingsActivity.this, SettingsActivity.this.getString(R.string.changed_to, stringDateSelected), Toast.LENGTH_SHORT).show();
                                Utils.setNotificationsForNextWeek(SettingsActivity.this);
                            }
                        }, instance.get(Calendar.HOUR_OF_DAY), instance.get(Calendar.MINUTE), true);
                        timePickerDialog.setTitle(SettingsActivity.this.getString(R.string.select_time));
                        timePickerDialog.show();

                    }
                }, instance.get(Calendar.YEAR),instance.get(Calendar.MONTH),instance.get(Calendar.DAY_OF_MONTH));

//                datePickerDialog.getDatePicker().setMinDate(instance.getTimeInMillis()+ 1*24*60*60*1000); // minDate NextDay
                datePickerDialog.getDatePicker().setMinDate(instance.getTimeInMillis());
                datePickerDialog.show();

            }
        });
    }
}
