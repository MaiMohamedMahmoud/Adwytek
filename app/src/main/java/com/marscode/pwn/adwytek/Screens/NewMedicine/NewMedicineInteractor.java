package com.marscode.pwn.adwytek.Screens.NewMedicine;

import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.marscode.pwn.adwytek.Model.Dose;
import com.marscode.pwn.adwytek.Model.Medicine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewMedicineInteractor implements NewMedicineInterface.NewMedicineInteractor {

    FirebaseAuth auth;
    public DatabaseReference databaseReference;
    public DatabaseReference medicineRef;

    NewMedicineInteractor(FirebaseAuth auth, DatabaseReference database) {
        this.auth = auth;
        databaseReference = database;
    }


    @Override
    public void addNewMedicine(Medicine medicine, List<Dose> doseList, OnFinishedListener onFinishedListener) {
        String key = databaseReference.child("medicine").push().getKey();

        Map<String, Object> medicineValues = medicine.toMap(databaseReference, key);
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("MedcineId", key);

        childUpdates.put("/medicine/" + key, medicineValues);
        childUpdates.put("/user-medicine/" + auth.getUid() + "/" + key, medicineValues);
        databaseReference.updateChildren(childUpdates);
        Log.i("yarab before dose", "" + doseList.size());

        Map<String, Object> medicine_childUpdates = new HashMap<>();
        for (int i = 0; i < doseList.size(); i++) {
            Dose dose = new Dose(doseList.get(i).day, doseList.get(i).time, doseList.get(i).dose_quantity);
            String dosekey = databaseReference.child("medicine").child(key).child("doses").push().getKey();
            Log.i("yarab Dose Key", dosekey + "size" + doseList.size());
            medicine_childUpdates.put("/doses/" + dosekey, dose.toMap());
            //child_Updates_.put("user-medicine/" + auth.getUid() + "/" + key + "/doses/" + dosekey, dose.toMap());
            //databaseReference.child("medicine/" + key).child("doses").push().setValue(dose.toMap());
            //databaseReference.child("user-medicine/" + auth.getUid() + "/" + key).child("doses").push().setValue(dose.toMap());
        }
        databaseReference.child("user-medicine").child(auth.getUid()).child(key).updateChildren(medicine_childUpdates);
        databaseReference.child("medicine").child(key).updateChildren(medicine_childUpdates);
        onFinishedListener.onFinish();
    }
}
