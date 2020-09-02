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

    LinearLayout phoneContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ViewPager viewPager = findViewById(R.id.viewPager);

        RegisterationPagerAdapter registerationPagerAdapter = new RegisterationPagerAdapter(getSupportFragmentManager(), 0);
        registerationPagerAdapter.addFragmet(new LoginFragment());
        registerationPagerAdapter.addFragmet(new RegisterFragment());
        viewPager.setAdapter(registerationPagerAdapter);

        //phoneContainer = findViewById(R.id.phone_container);

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
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.register_phone, null);
        phoneContainer.addView(rowView, phoneContainer.getChildCount() - 1);
    }

    public void onDelete(View v) {
        phoneContainer.removeView((View) v.getParent());
    }
}