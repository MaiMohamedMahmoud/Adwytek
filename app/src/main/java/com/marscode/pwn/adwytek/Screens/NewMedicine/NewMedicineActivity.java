package com.marscode.pwn.adwytek.Screens.NewMedicine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.FragmentBuilder;
import com.marscode.pwn.adwytek.Screens.MedicineList.MedicineListFragment;

public class NewMedicineActivity extends FragmentBuilder {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medicine);
        setFragment(R.id.fragment_container_newMedicine, new NewMedicineFragment());
    }
}