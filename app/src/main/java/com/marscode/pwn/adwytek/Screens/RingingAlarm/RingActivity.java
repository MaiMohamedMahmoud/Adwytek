package com.marscode.pwn.adwytek.Screens.RingingAlarm;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.TextView;

import com.marscode.pwn.adwytek.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RingActivity extends AppCompatActivity {
    Ringtone ringtone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ring_activity);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);


        ringtone = RingtoneManager.getRingtone(getApplicationContext(), Settings.System.DEFAULT_RINGTONE_URI);
        ringtone.play();
    }
}
