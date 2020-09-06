package com.marscode.pwn.adwytek.Screens.SingUp;

import android.os.Bundle;

import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.FragmentBuilder;

public class RegisterActivity extends FragmentBuilder {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setFragment(R.id.fragment_container_register, new RegisterFragment());
    }


}