package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.util.Log;

import com.marscode.pwn.adwytek.Model.Medicine;

import java.util.ArrayList;
import java.util.List;

class MedicineListInteractor implements MedicineListInterface.MedicineListInteractor {
    List<Medicine> medicineList;

    @Override
    public List<Medicine> getMedicineList() {
        medicineList = new ArrayList<>();
        Medicine medicineOmega = new Medicine("Omega");
        Medicine medicineVitamin = new Medicine("Vitamin");
        Medicine medicineVitaminA = new Medicine("Vitamin A");
        Medicine medicineVitaminB = new Medicine("Vitamin B");
        Medicine medicineVitaminD = new Medicine("Vitamin D");
        Medicine medicineVitaminE = new Medicine("Vitamin E");
        Medicine medicineVitaminF = new Medicine("Vitamin F");
        medicineList.add(medicineOmega);
        medicineList.add(medicineVitamin);
        medicineList.add(medicineVitaminA);
        medicineList.add(medicineVitaminB);
        medicineList.add(medicineVitaminD);
        medicineList.add(medicineVitaminE);
        medicineList.add(medicineVitaminF);


        return medicineList;
    }
}
