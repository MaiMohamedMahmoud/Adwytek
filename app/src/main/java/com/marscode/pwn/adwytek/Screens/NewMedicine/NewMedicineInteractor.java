package com.marscode.pwn.adwytek.Screens.NewMedicine;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.marscode.pwn.adwytek.Model.Dose;
import com.marscode.pwn.adwytek.Model.Medicine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewMedicineInteractor implements NewMedicineInterface.NewMedicineInteractor {

    FirebaseAuth auth;
    public DatabaseReference databaseReference;
    public DatabaseReference medicineRef;

    NewMedicineInteractor(FirebaseAuth auth, DatabaseReference database) {
        this.auth = auth;
        databaseReference = database;
    }

    @Override
    public void addNewMedicine(Medicine medicine) {
        Map<String, Object> medicineValues = medicine.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        String key = databaseReference.child("medicine").push().getKey();
        Map<String, Object> map = new HashMap<>();
        map.put("MedcineId", key);

        childUpdates.put("/medicine/" + key, medicineValues);
        childUpdates.put("/user-medicine/" + auth.getUid() + "/" + key, medicineValues);
        databaseReference.updateChildren(childUpdates);
    }
}
