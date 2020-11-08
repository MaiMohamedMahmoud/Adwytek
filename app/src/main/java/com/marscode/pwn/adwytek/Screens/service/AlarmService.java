package com.marscode.pwn.adwytek.Screens.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.RingingAlarm.RingActivity;

import androidx.annotation.Nullable;

import static com.marscode.pwn.adwytek.Application.App.Channel_Id;

public class AlarmService extends IntentService {
    private static final String TAG = "AlarmService";
    private PowerManager.WakeLock wakeLock;
    private Ringtone ringtone;
    private Vibrator vibrator;

    public AlarmService() {
        super("Alarm service");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AlarmService:WakeLock");
        //make the notification disappear after 10 min or after the work is done
        wakeLock.acquire(600000);
        Log.d(TAG, "wakeLock acquire");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent");
        Intent newIntent = new Intent(this, RingActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, newIntent, 0);
        String alarmTitle = intent.getStringExtra(getString(R.string.alarmtitle_Extra_string));

        //check for version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "onHandleIntent: Check version");

            //Build notification using channelID which created before in notificationChannelFun.
            Notification notification = new Notification.Builder(this, Channel_Id).
                    setContentTitle(alarmTitle).
                    setContentText(getString(R.string.alarmString)).
                    setSmallIcon(R.drawable.ic_baseline_access_alarm_24).
                    setContentIntent(pendingIntent).
                    build();
            //start your service and start it using foreground not as a background service so it didn't
            //get killed or through an exception ..
            //or even use jobIntentService

            startForeground(1, notification);
            startActivity(newIntent);
            // Vibrate for 1000 milliseconds
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            ringtone.play();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ringtone.setLooping(true);
            }
            SystemClock.sleep(60000);


        } else {
            startActivity(newIntent);
            //deprecated in API < 26
            vibrator.vibrate(1000);
            ringtone.play();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        wakeLock.release();
        ringtone.stop();
        vibrator.cancel();
        Log.d(TAG, "onDestroy: wakeLock ");

    }
}
