package com.marscode.pwn.adwytek.Screens.RingingAlarm;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.service.AlarmService;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class RingActivity extends AppCompatActivity {
    Ringtone ringtone;
    TextView alarmtxt;
    Button dismissAlarm;
    Vibrator vibrator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ring_activity);
        alarmtxt = findViewById(R.id.alarmtxt);
        alarmtxt.setText(getString(R.string.alarmString));
        dismissAlarm = findViewById(R.id.dismiss);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        //dismissAlarm.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }

    public void onDismissClick(View view) {
        Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
        getApplicationContext().stopService(intentService);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void stop() {
//        ringtone.stop();
//        vibrator.cancel();
    }

//    @Override
//    public void onClick(View view) {
//        finish();
//    }
}
