package com.marscode.pwn.adwytek.Model;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Medicine {

    public String name;
    public Date start_date;
    public Date end_date;
    public String frequency_of_intake;
    public HashMap<String, Dose> doses;

    public Medicine() {

    }

    public Medicine(String name, Date start_date, Date end_date, String frequency_of_intake) {
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.frequency_of_intake = frequency_of_intake;
    }

    public String getName() {
        return name;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public String getFrequency_of_intake() {
        return frequency_of_intake;
    }

//    public List<Dose> getDoseList() {
//        return doses;
//    }

    // ...


    @Exclude
    public Map<String, Object> toMap(DatabaseReference databaseReference, String medicineKey) {

        HashMap<String, Object> medicine = new HashMap<>();
        medicine.put("name", name);
        medicine.put("start_date", start_date);
        medicine.put("end_date", end_date);
        medicine.put("frequency_of_intake", frequency_of_intake);
        return medicine;
    }
}