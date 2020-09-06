package com.marscode.pwn.adwytek.Screens.SignIn;

import android.os.Bundle;

import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.FragmentBuilder;

public class LoginActivity extends FragmentBuilder {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setFragment(R.id.fragment_container_login,new LoginFragment());


    }


}