package com.marscode.pwn.adwytek.Screens.IntroPage;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;
import com.github.paolorotolo.appintro.model.SliderPagerBuilder;
import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.PatientCareGiver.PatientCareGiverActivity;
import com.marscode.pwn.adwytek.Screens.SingUp.RegisterActivity;

public class IntroActivity extends AppIntro {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showIntroSlides();
        if (restorePreference()) {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        } else {
            savePreference();
        }
    }

    public boolean restorePreference() {
        //retrieve data from the sharedPref to know if the user already see the IntroPage before or not
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.save_ref_is_introPage), MODE_PRIVATE);
        return sharedPreferences.getBoolean(getString(R.string.IsIntroOpen), false);

    }

    public void savePreference() {
        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.save_ref_is_introPage), MODE_PRIVATE);
        // Creating an Editor object
        // to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putBoolean(getString(R.string.IsIntroOpen), true);
        myEdit.commit();
    }


    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(currentFragment.getContext(), RegisterActivity.class);
        currentFragment.getActivity().startActivity(intent);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(currentFragment.getContext(), RegisterActivity.class);
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