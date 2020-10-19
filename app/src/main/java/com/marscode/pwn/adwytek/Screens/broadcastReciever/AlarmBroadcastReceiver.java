package com.marscode.pwn.adwytek.Screens.broadcastReciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.marscode.pwn.adwytek.Screens.RingingAlarm.RingActivity;

import static android.content.ContentValues.TAG;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");

        Intent newIntent = new Intent(context, RingActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(newIntent);
    }
}
