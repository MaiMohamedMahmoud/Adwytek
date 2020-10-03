package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.marscode.pwn.adwytek.Model.Medicine;

import java.util.ArrayList;
import java.util.List;

class MedicineListPresenter implements MedicineListInterface.MedicineListPresenter, MedicineListInterface.MedicineListInteractor.OnFinishedListener {
    List<Medicine> medicineList;
    MedicineListInterface.MedicineListView medicineListView;
    MedicineListInterface.MedicineListInteractor medicineListInteractor;

    MedicineListPresenter(MedicineListInterface.MedicineListView medicineListView, MedicineListInterface.MedicineListInteractor medicineListInteractor) {
        this.medicineListView = medicineListView;
        this.medicineListInteractor = medicineListInteractor;
    }

    @Override
    public void getMedicineList() {
        medicineListInteractor.getMedicineList(this);
    }

    @Override
    public void addNewMedicine() {
        medicineListInteractor.addNewMedicine();
    }


    @Override
    public void onFinished(DataSnapshot dataSnapshot) {
        medicineList = new ArrayList<>();
        // Get Medicine list and use the values to update the UI
        int i = 0;
        Log.i("yarab get", dataSnapshot.getChildrenCount() + "");
        for (DataSnapshot med : dataSnapshot.getChildren()) {
            Medicine medicineObj = med.getValue(Medicine.class);
            Log.i("yarab get" + i++, medicineObj.start_date + " " + medicineObj.frequency_of_intake + " " + medicineObj.Doses.size() + " " + medicineObj.name + "");

            medicineList.add(medicineObj);
        }

        medicineListView.setMedicineList(medicineList);
    }
}
