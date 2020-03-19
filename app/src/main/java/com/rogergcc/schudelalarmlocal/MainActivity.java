package com.rogergcc.schudelalarmlocal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private TextView textAuthor;
    private TextView textSentence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.prefs = getSharedPreferences(getString(R.string.app_name), 0);
     
        Utils.setNotificationsForNextWeek(this);
        this.textSentence = (TextView) findViewById(R.id.text_sentence);
        this.textAuthor = (TextView) findViewById(R.id.text_author);
        displayQuote();
    }

    private void displayQuote() {
        int i;
        String str = Utils.TODAY;
        try {
            JSONArray jSONArray = new JSONArray(readFileFromAssets(this));
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            boolean equals = this.prefs.getString(str, "").equals(format);
            String str2 = Utils.TODAYS_INT;
            if (equals) {
                i = this.prefs.getInt(str2, 1);
            } else {
                double random = Math.random();
                double length = (double) (jSONArray.length() - 1);
                Double.isNaN(length);
                int i2 = (int) ((random * length) + 1.0d);
                SharedPreferences.Editor edit = this.prefs.edit();
                edit.putInt(str2, i2).commit();
                edit.putString(str, format).commit();
                i = i2;
            }
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            this.textSentence.setText(jSONObject.getString("text"));
            TextView textView = this.textAuthor;
            StringBuilder sb = new StringBuilder();
            sb.append(jSONObject.getString("author"));
            sb.append(" - ");
            sb.append(jSONObject.getString("authordata"));
            sb.append("\n(");
            sb.append(jSONObject.getString("date"));
            sb.append(")");
            textView.setText(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String readFileFromAssets(Context ctx) {
        StringBuilder strFile = new StringBuilder();
        try {
            BufferedReader reader = null;
            InputStream inputStream = ctx.getAssets().open("quotes.txt");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                strFile.append(mLine);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Log.i("tag", strFile.toString());

        return strFile.toString();
    }


    private java.lang.String readFileFromAssets() {
        /*
            r6 = this;
            java.lang.String r0 = ""
            r1 = 0
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ IOException -> 0x003f, all -> 0x0038 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x003f, all -> 0x0038 }
            android.content.res.AssetManager r4 = r6.getAssets()     // Catch:{ IOException -> 0x003f, all -> 0x0038 }
            java.lang.String r5 = "quotes.txt"
            java.io.InputStream r4 = r4.open(r5)     // Catch:{ IOException -> 0x003f, all -> 0x0038 }
            java.lang.String r5 = "UTF-8"
            r3.<init>(r4, r5)     // Catch:{ IOException -> 0x003f, all -> 0x0038 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x003f, all -> 0x0038 }
        L_0x0019:
            java.lang.String r1 = r2.readLine()     // Catch:{ IOException -> 0x0036, all -> 0x0033 }
            if (r1 == 0) goto L_0x002f
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0036, all -> 0x0033 }
            r3.<init>()     // Catch:{ IOException -> 0x0036, all -> 0x0033 }
            r3.append(r0)     // Catch:{ IOException -> 0x0036, all -> 0x0033 }
            r3.append(r1)     // Catch:{ IOException -> 0x0036, all -> 0x0033 }
            java.lang.String r0 = r3.toString()     // Catch:{ IOException -> 0x0036, all -> 0x0033 }
            goto L_0x0019
        L_0x002f:
            r2.close()     // Catch:{ IOException -> 0x0045 }
            goto L_0x0045
        L_0x0033:
            r0 = move-exception
            r1 = r2
            goto L_0x0039
        L_0x0036:
            r1 = r2
            goto L_0x0040
        L_0x0038:
            r0 = move-exception
        L_0x0039:
            if (r1 == 0) goto L_0x003e
            r1.close()     // Catch:{ IOException -> 0x003e }
        L_0x003e:
            throw r0
        L_0x003f:
        L_0x0040:
            if (r1 == 0) goto L_0x0045
            r1.close()     // Catch:{ IOException -> 0x0045 }
        L_0x0045:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.walkiriaapps.frasescelebres.MainActivity.readFileFromAssets():java.lang.String");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.action_settings) {
            return super.onOptionsItemSelected(menuItem);
        }
        startActivity(new Intent(this, SettingsActivity.class));
        return true;
    }

}
