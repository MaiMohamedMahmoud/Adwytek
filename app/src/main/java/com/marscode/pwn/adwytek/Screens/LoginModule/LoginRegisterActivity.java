package com.marscode.pwn.adwytek.Screens.LoginModule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.marscode.pwn.adwytek.R;

import java.util.ArrayList;
import java.util.List;

public class LoginRegisterActivity extends AppCompatActivity {

    LoginFragment mLoginFragment;
    RegisterFragment mRegisterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ViewPager viewPager = findViewById(R.id.viewPager);

        mLoginFragment = new LoginFragment();
        mRegisterFragment = new RegisterFragment();
        RegisterationPagerAdapter registerationPagerAdapter = new RegisterationPagerAdapter(getSupportFragmentManager(), 0);
        registerationPagerAdapter.addFragmet(mLoginFragment);
        registerationPagerAdapter.addFragmet(mRegisterFragment);
        viewPager.setAdapter(registerationPagerAdapter);

    }

    public class RegisterationPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> mFragmentList = new ArrayList<>();

        public RegisterationPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragmet(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }

    public void onAdd(View v) {
        mRegisterFragment.onAdd(v);
    }

    public void onDelete(View v) {
        mRegisterFragment.onDelete(v);
    }

}