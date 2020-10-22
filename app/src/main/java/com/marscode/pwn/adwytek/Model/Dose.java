package com.marscode.pwn.adwytek.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Dose {

    public String id;
    public String day;
    public String time;
    public String dose_quantity;

    public Dose() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Dose(String day, String time, String dose_quantity) {
        this.day = day;
        this.time = time;
        this.dose_quantity = dose_quantity;
    }

    public Dose(String id, String day, String time, String dose_quantity) {
        this.id = id;
        this.day = day;
        this.time = time;
        this.dose_quantity = dose_quantity;
    }


    @Exclude
    public  Map<String, Object> toMap() {
        HashMap<String, Object> Dose = new HashMap<>();
        Dose.put("day", day);
        Dose.put("dose_quantity", dose_quantity);
        Dose.put("time", time);
        return Dose;
    }

}
