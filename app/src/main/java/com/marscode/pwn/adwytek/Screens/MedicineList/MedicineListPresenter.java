package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.marscode.pwn.adwytek.Model.Dose;
import com.marscode.pwn.adwytek.Model.Medicine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    public void setAlarm(Context context, List<Dose> doseList) {


        for (int i = 0; i < doseList.size(); i++) {
            String string = doseList.get(i).time;
            String[] parts = string.split(":");
            String hours = parts[0];
            String min = parts[1];
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours));
            calendar.set(Calendar.MINUTE, Integer.parseInt(min));
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH);
            medicineListInteractor.setAlarm(context, calendar);

        }
    }


    @Override
    public void onFinished(DataSnapshot dataSnapshot) {
        medicineList = new ArrayList<>();
        // Get Medicine list and use the values to update the UI
        for (DataSnapshot med : dataSnapshot.getChildren()) {
            Medicine medicineObj = med.getValue(Medicine.class);
            setAlarm(context, medicineObj.getDoseList());
            medicineList.add(medicineObj);

        }
        medicineListView.setMedicineList(medicineList);
    }
}
