package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.util.Log;
import android.widget.Toast;

import com.marscode.pwn.adwytek.Model.Medicine;

import java.util.List;

class MedicineListPresenter implements MedicineListInterface.MedicineListPresenter {

    MedicineListInterface.MedicineListView medicineListView;
    MedicineListInterface.MedicineListInteractor medicineListInteractor;

    MedicineListPresenter(MedicineListInterface.MedicineListView medicineListView, MedicineListInterface.MedicineListInteractor medicineListInteractor) {
        this.medicineListView = medicineListView;
        this.medicineListInteractor = medicineListInteractor;
    }

    @Override
    public List<Medicine> getMedicineList() {
        List<Medicine> medicineList = medicineListInteractor.getMedicineList();
        medicineListView.setMedicineList(medicineList);
        return medicineList;
    }
}
