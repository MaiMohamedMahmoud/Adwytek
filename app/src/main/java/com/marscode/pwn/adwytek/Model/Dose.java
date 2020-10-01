package com.marscode.pwn.adwytek.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@IgnoreExtraProperties
public class Dose {

    public String day;
    public Date time;
    public String dose_quantity;

    public Dose(String day, Date time, String dose_quantity) {
        this.day = day;
        this.time = time;
        this.dose_quantity = dose_quantity;
    }
}
