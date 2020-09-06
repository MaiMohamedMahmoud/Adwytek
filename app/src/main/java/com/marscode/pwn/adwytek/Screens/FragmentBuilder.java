package com.marscode.pwn.adwytek.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentBuilder extends AppCompatActivity {

    public void setFragment(int resId, Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentById = fragmentManager.findFragmentById(resId);
        if (fragmentById == null) {
            fragmentById = fragment;
            fragmentManager
                    .beginTransaction()
                    .add(resId, fragmentById)
                    .commit();
        }

    }

}

