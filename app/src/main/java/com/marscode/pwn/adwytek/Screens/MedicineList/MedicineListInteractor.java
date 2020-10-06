package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.marscode.pwn.adwytek.Model.Dose;
import com.marscode.pwn.adwytek.Model.Medicine;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

class MedicineListInteractor implements MedicineListInterface.MedicineListInteractor {
    List<String> days;
    FirebaseAuth auth;
    public DatabaseReference databaseReference;
    public DatabaseReference medicineRef;

    MedicineListInteractor(FirebaseAuth auth, DatabaseReference database) {
        this.auth = auth;
        this.databaseReference = database;

    }

    @Override
    public void getMedicineList(final OnFinishedListener listener) {

        medicineRef = databaseReference.child("medicine");
        medicineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onFinished(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.i("yarab", "loadMedicine:onCancelled", databaseError.toException());
                // ...
            }
        });
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
        Dose dose2 = new Dose("Tu", c, "3");
        List<Dose> doseList = new ArrayList<>();
        doseList.add(dose1);
        doseList.add(dose2);


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

    /**
     * static medicine list
     */

    //        Date c = Calendar.getInstance().getTime();
    //        System.out.println("Current time => " + c);
    //        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    //        String formattedDate = df.format(c);

    //        Dose dose1 = new Dose("Su", c, "2");
    //        List<Dose> doseList = new ArrayList<>();
    //        doseList.add(dose1);

    //        Medicine medicineOmega = new Medicine("Omega", c, c, "3", doseList);
    //       medicineList.add(medicineOmega);

}
