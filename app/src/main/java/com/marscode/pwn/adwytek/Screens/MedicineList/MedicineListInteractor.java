package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.marscode.pwn.adwytek.Model.Dose;
import com.marscode.pwn.adwytek.Model.Medicine;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

class MedicineListInteractor implements MedicineListInterface.MedicineListInteractor {
    List<Medicine> medicineList;
    List<String> days;
    FirebaseAuth auth;
    DatabaseReference databaseReference;

    MedicineListInteractor(FirebaseAuth auth, DatabaseReference database) {
        this.auth = auth;
        this.databaseReference = database;
    }

    @Override
    public List<Medicine> getMedicineList() {
        medicineList = new ArrayList<>();
        days = new ArrayList<>();
        days.add("Tu");
        days.add("Sat");

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);


//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
//        String formattedDate = df.format(c);

        Dose dose1 = new Dose("Su", c, "2");
        List<Dose> doseList = new ArrayList<>();
        doseList.add(dose1);


        Medicine medicineOmega = new Medicine("Omega", c, c, "3", doseList);
        Medicine medicineVitamin = new Medicine("Vitamin", c, c, "2", doseList);
        Medicine medicineVitaminA = new Medicine("Vitamin A", c, c, "1", doseList);
        Medicine medicineVitaminB = new Medicine("Vitamin B", c, c, "1", doseList);
        Medicine medicineVitaminD = new Medicine("Vitamin D", c, c, "2", doseList);
        Medicine medicineVitaminE = new Medicine("Vitamin E", c, c, "3", doseList);
        Medicine medicineVitaminF = new Medicine("Vitamin F", c, c, "1", doseList);
        medicineList.add(medicineOmega);
        medicineList.add(medicineVitamin);
        medicineList.add(medicineVitaminA);
        medicineList.add(medicineVitaminB);
        medicineList.add(medicineVitaminD);
        medicineList.add(medicineVitaminE);
        medicineList.add(medicineVitaminF);

        return medicineList;
    }

    @Override
    public void addNewMedicine() {
        days = new ArrayList<>();
        days.add("Tu");
        days.add("Sat");

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);


//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
//        String formattedDate = df.format(c);

        Dose dose1 = new Dose("Su", c, "2");
        List<Dose> doseList = new ArrayList<>();
        doseList.add(dose1);


        Medicine medicineOmega = new Medicine("Omega", c, c, "3", doseList);
        Map<String, Object> medicineValues = medicineOmega.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        String key = databaseReference.child("medicine").push().getKey();
        Map<String, Object> map = new HashMap<>();
        map.put("MedcineId", key);

        childUpdates.put("/medicine/" + key, medicineValues);
        childUpdates.put("/user-medicine/" + auth.getUid() + "/" + key, "");
        databaseReference.updateChildren(childUpdates);


    }


}
