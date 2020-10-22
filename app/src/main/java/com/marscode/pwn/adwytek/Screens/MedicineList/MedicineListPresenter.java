package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.marscode.pwn.adwytek.Model.Dose;
import com.marscode.pwn.adwytek.Model.Medicine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

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
//            for (int j = 0; j < medicineList.get(i).doses.size(); j++) {
//                String string = medicineList.get(i).doses.get(j).time;
//                String[] parts = string.split(":");
//                String hours = parts[0];
//                String min = parts[1];
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(System.currentTimeMillis());
//                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours));
//                calendar.set(Calendar.MINUTE, Integer.parseInt(min));
//                Toast.makeText(context, min + " min" + hours + " hours", Toast.LENGTH_LONG).show();
//                Random random = new Random();
//
//                if(!calendar.before(Calendar.getInstance())){
//                    medicineListInteractor.setAlarm(context, calendar);
//                }
//            }
        }
    }

    @Override
    public void set(Context context, Calendar calendar) {
        medicineListInteractor.setAlarm(context, calendar);
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
}
