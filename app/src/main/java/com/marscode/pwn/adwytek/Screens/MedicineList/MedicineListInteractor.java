package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.marscode.pwn.adwytek.Screens.broadcastReciever.AlarmBroadcastReceiver;

import java.util.Calendar;
import java.util.List;

class MedicineListInteractor implements MedicineListInterface.MedicineListInteractor {
    List<String> days;
    FirebaseAuth auth;
    public DatabaseReference databaseReference;
    public DatabaseReference medicineRef;

    MedicineListInteractor(FirebaseAuth auth, DatabaseReference database) {
        this.auth = auth;
        this.databaseReference = database;
    }

    @Override
    public void getMedicineListByUserId(final OnFinishedListener listener) {

        medicineRef = databaseReference.child("user-medicine").child(auth.getUid());
        medicineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onFinished(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.i("yarab", "loadMedicine:onCancelled", databaseError.toException());
                // ...
            }
        });
    }

    @Override
    public void setAlarm(Context context,Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);

        intent.putExtra("Title", "title");

        //requested code (0) here should be replaced with alarmID
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context,0, intent, 0);

        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                alarmPendingIntent
        );
    }
}



