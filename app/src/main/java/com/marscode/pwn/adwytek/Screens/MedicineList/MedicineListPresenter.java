package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.gson.Gson;
import com.marscode.pwn.adwytek.Model.Dose;
import com.marscode.pwn.adwytek.Model.Medicine;
import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.Widget.MedicineWidgetService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import androidx.annotation.IntegerRes;

class MedicineListPresenter implements MedicineListInterface.MedicineListPresenter, MedicineListInterface.MedicineListInteractor.OnFinishedListener {
    List<Medicine> medicineList;
    Context context;
    MedicineListInterface.MedicineListView medicineListView;
    MedicineListInterface.MedicineListInteractor medicineListInteractor;

    MedicineListPresenter(Context context, MedicineListInterface.MedicineListView medicineListView, MedicineListInterface.MedicineListInteractor medicineListInteractor) {
        this.context = context;
        this.medicineListView = medicineListView;
        this.medicineListInteractor = medicineListInteractor;
    }

    @Override
    public void getMedicineList() {
        medicineListInteractor.getMedicineListByUserId(this);
    }

    @Override
    public void setAlarm(Context context, List<Medicine> medicineList) {

        for (int i = 0; i < medicineList.size(); i++) {
            final HashMap<String, Dose> hashMap = medicineList.get(i).doses;
            final List<Dose> listing = new ArrayList<>();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                hashMap.keySet().forEach(new Consumer<String>() {
                    @Override
                    public void accept(String key) {
                        System.out.println(key + " yarab" + hashMap.get(key));
                        Log.i("yarab", key + " " + hashMap.get(key));
                        listing.add(hashMap.get(key));
                    }
                });
            }

            for (int j = 0; i < listing.size(); i++) {

                String string = listing.get(j).time;
                String[] parts = string.split(":");
                String hours = parts[0];
                String min = parts[1];
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours));
                calendar.set(Calendar.MINUTE, Integer.parseInt(min));
                Toast.makeText(context, min + " min" + hours + " hours", Toast.LENGTH_LONG).show();

                if (!calendar.before(Calendar.getInstance())) {
                    medicineListInteractor.setAlarm(context, calendar, new Random().nextInt(Integer.MAX_VALUE));
                }
            }
        }
    }


    @Override
    public void onFinished(DataSnapshot dataSnapshot) {
        medicineList = new ArrayList<>();
        // Get Medicine list and use the values to update the UI
        for (DataSnapshot med : dataSnapshot.getChildren()) {
            Medicine medicineObj = med.getValue(Medicine.class);
            medicineList.add(medicineObj);

        }

        medicineListView.setMedicineList(medicineList);

    }


    @Override
    public void setListMedicineSharedPreference(List<Medicine> medicineList) {
        List<Medicine> medList = medicineList;
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.MedicineListPref), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(medList);
        prefsEditor.putString(context.getString(R.string.MedicineListObj), json);
        prefsEditor.commit();
    }
}
