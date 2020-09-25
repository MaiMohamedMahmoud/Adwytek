package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.util.Log;

import com.marscode.pwn.adwytek.Model.Medicine;

import java.util.ArrayList;
import java.util.List;

class MedicineListInteractor implements MedicineListInterface.MedicineListInteractor {
    List<Medicine> medicineList;
    List<String> days;

    @Override
    public List<Medicine> getMedicineList() {
        medicineList = new ArrayList<>();
        days = new ArrayList<>();
        days.add("Tu");
        days.add("Sat");

        Medicine medicineOmega = new Medicine("Omega", "23", "26", "3", days);
        Medicine medicineVitamin = new Medicine("Vitamin", "13", "26", "2", days);
        Medicine medicineVitaminA = new Medicine("Vitamin A", "20", "26", "1", days);
        Medicine medicineVitaminB = new Medicine("Vitamin B", "9", "26", "3", days);
        Medicine medicineVitaminD = new Medicine("Vitamin D", "4", "26", "2", days);
        Medicine medicineVitaminE = new Medicine("Vitamin E", "5", "26", "3", days);
        Medicine medicineVitaminF = new Medicine("Vitamin F", "21", "26", "1", days);
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
