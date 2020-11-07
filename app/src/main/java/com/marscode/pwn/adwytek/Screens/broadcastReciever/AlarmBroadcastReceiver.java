package com.marscode.pwn.adwytek.Screens.broadcastReciever;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.widget.Toast;

import com.marscode.pwn.adwytek.Screens.RingingAlarm.RingActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    int today;
    List<Integer> listDays;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        int alarmId = intent.getIntExtra("alarmID", 0);
        long startDateLong = intent.getLongExtra("startDate", -1);
        long endDateLong = intent.getLongExtra("endDate", -1);
        Date startDate = new Date(startDateLong);
        Date endDate = new Date(endDateLong);
        List<String> dayList = (ArrayList<String>) intent.getSerializableExtra("dayList");
        Log.i("yarab  ", "inside broadcast recicend");

        if (alarmIsValid(startDate, endDate)) {
            if (alarmIsToday(dayList)) {
                Intent newIntent = new Intent(context, RingActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(newIntent);
            }
        } else {
            cancelAlarm(alarmId, context);
        }


    }

    private boolean alarmIsValid(Date startDate, Date endDate) {
        Log.i("yarab", "alam valid");
        listDays = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        today = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        //Log.i("yarab Date alarm valid ", calendar.getTime().toString() + " is after startDate" + calendar.getTime().after(startDate) + " is equal startDate" + calendar.getTime().equals(startDate) + " is equal endDate " + calendar.getTime().equals(endDate) + " is before endDate" + calendar.getTime().before(endDate));
        if ((calendar.getTime().after(startDate) || calendar.getTime().equals(startDate)) && (calendar.getTime().equals(endDate) || calendar.getTime().before(endDate))) {
            //Log.i("yarab Date", calendar.getTime().toString() + " List of days " + listDays.size());
            return true;
        } else {
            return false;
        }
    }

    private void cancelAlarm(int alarmId, Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        alarmManager.cancel(alarmPendingIntent);

        //TODO ONLY FOR TESTING DELETE IT
        String toastText = String.format("Alarm cancelled  ", alarmId);
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();

    }

    private boolean alarmIsToday(List<String> listOfDays) {
        for (int i = 0; i < listOfDays.size(); i++) {
            switch (listOfDays.get(i)) {
                case "Mo":
                    Log.i("yarab Date", "mo");
                    listDays.add(Calendar.MONDAY);
                    break;
                case "Tue":
                    Log.i("yarab Date", "te");
                    listDays.add(Calendar.TUESDAY);
                    break;
                case "Wen":
                    Log.i("yarab Date", "wen");
                    listDays.add(Calendar.WEDNESDAY);
                    break;
                case "Thu":
                    Log.i("yarab Date", "th");
                    listDays.add(Calendar.THURSDAY);
                    break;
                case "Fr":
                    Log.i("yarab Date", "fr");
                    listDays.add(Calendar.FRIDAY);
                    break;
                case "Sa":
                    Log.i("yarab Date", "sat");
                    listDays.add(Calendar.SATURDAY);
                    break;
                case "Su":
                    Log.i("yarab Date", "sun");
                    listDays.add(Calendar.SUNDAY);
                    break;

            }
        }

        if (listDays.contains(today)) {
            Log.i("yarab", "today there is an alarm");
            return true;
        } else {
            return false;
        }

    }
}
