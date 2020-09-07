package com.marscode.pwn.adwytek.Screens.MedicineList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.FragmentBuilder;

public class MedicineListActivity extends FragmentBuilder {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);
        setFragment(R.id.fragment_container_medicineList, new MedicineListFragment());
    }
}