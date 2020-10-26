package com.marscode.pwn.adwytek.Screens.MedicineList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.FragmentBuilder;

public class MedicineListActivity extends FragmentBuilder {
    //Initialize BottomNavigation
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);
        //setFragment(R.id.fragment_container_medicineList, new MedicineListFragment());

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Initialize nav controller using navHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        //setup nav controller
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }
}