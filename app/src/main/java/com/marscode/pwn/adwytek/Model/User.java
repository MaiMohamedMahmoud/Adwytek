package com.marscode.pwn.adwytek.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class User {

    public String uid;
    public String user_name;
    public String email;
    public String age;
    public String caregiver_phone;
    public List<String>  patient_phone;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User( String username, String email, String age, String caregiver_phone, List<String> patient_phone) {

        this.user_name = username;
        this.email = email;
        this.age = age;
        this.caregiver_phone = caregiver_phone;
        this.patient_phone = patient_phone;

    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> user = new HashMap<>();
        user.put("uid", uid);
        user.put("Name", user_name);
        user.put("Email", email);
        user.put("Age", age);
        user.put("caregiver_phone",  caregiver_phone);
        user.put("patient_phone", patient_phone);


        return user;
    }
}
