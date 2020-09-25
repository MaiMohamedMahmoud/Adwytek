package com.marscode.pwn.adwytek.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Medicine {

    public String name;
    public String start_date;
    public String end_date;
    public String times_per_day;
    public List<String> days;


    public Medicine(String name, String start_date, String end_date, String times_per_day, List<String> days) {
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.times_per_day = times_per_day;
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getTimes_per_day() {
        return times_per_day;
    }

    public List<String> getDays() {
        return days;
    }

// ...

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> medicine = new HashMap<>();
        medicine.put("name", name);
        medicine.put("start_date", start_date);
        medicine.put("end_date", end_date);
        medicine.put("times_per_day", times_per_day);
        medicine.put("Days", days);

        return medicine;
    }
}