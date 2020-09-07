package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.util.Log;

import com.marscode.pwn.adwytek.Model.Medicine;

import java.util.ArrayList;
import java.util.List;

class MedicineListInteractor implements MedicineListInterface.MedicineListInteractor {
    List<Medicine> medicineList;

    @Override
    public List<Medicine> getMedicineList() {
        Log.i("yarab", "IN Interactor getMedicine");
        medicineList = new ArrayList<>();
        Medicine medicineOmega = new Medicine("Omega");
        Medicine medicineVitamin = new Medicine("Vitamin");
        medicineList.add(medicineOmega);
        medicineList.add(medicineVitamin);
        Log.i("yarab", medicineList.size() + "");
        return medicineList;
    }
}
