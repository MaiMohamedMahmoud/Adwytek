package com.marscode.pwn.adwytek.Screens.SplashScreens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.github.paolorotolo.appintro.model.SliderPagerBuilder;
import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.PatientCareGiver.PatientCareGiverActivity;
import com.marscode.pwn.adwytek.Screens.SignIn.LoginActivity;
import com.marscode.pwn.adwytek.Screens.SingUp.RegisterActivity;

public class SplashScreens extends AppIntro {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showIntroSlides();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(currentFragment.getContext(), PatientCareGiverActivity.class);
        currentFragment.getActivity().startActivity(intent);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(currentFragment.getContext(), PatientCareGiverActivity.class);
        currentFragment.getActivity().startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showIntroSlides() {
        SliderPage fragmentSlideOne = new SliderPagerBuilder()
                .description(getString(R.string.slide_one_down_text))
                .imageDrawable(R.drawable.logo1)
                .bgColor(getColor(R.color.colorPrimary))
                .build();
        SliderPage fragmentSlideTwo = new SliderPagerBuilder()
                .description(getString(R.string.slide_one_down_text))
                .imageDrawable(R.drawable.logo2)
                .bgColor(getColor(R.color.colorPrimary))
                .build();

        addSlide(AppIntroFragment.newInstance(fragmentSlideOne));
        addSlide(AppIntroFragment.newInstance(fragmentSlideTwo));
    }
}