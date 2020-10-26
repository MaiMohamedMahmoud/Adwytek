package com.marscode.pwn.adwytek.Screens.RingingAlarm;

import android.content.Context;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RingActivity extends AppCompatActivity implements View.OnClickListener {
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
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);


        ringtone = RingtoneManager.getRingtone(getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(1000);
        }
        ringtone.play();
        dismissAlarm.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void stop() {
        ringtone.stop();
        vibrator.cancel();
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
