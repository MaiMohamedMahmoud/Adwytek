package com.marscode.pwn.adwytek.Screens.PatientCareGiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.SingUp.RegisterActivity;

public class PatientCareGiverActivity extends AppCompatActivity {

    ImageView patient;
    ImageView caregiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_care_giver);
        patient = findViewById(R.id.img_patient);
        caregiver = findViewById(R.id.img_caregiver);
    }

    public void registerIntentPatient(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void registerIntentCaregiver(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}