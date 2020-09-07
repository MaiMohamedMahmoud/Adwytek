package com.marscode.pwn.adwytek.Model;

public class Medicine {
    String medicineName;

    public Medicine(String medName){
        this.medicineName = medName;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }
}
