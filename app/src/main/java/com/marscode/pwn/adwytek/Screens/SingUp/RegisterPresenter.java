package com.marscode.pwn.adwytek.Screens.SingUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.marscode.pwn.adwytek.Model.User;
import com.marscode.pwn.adwytek.R;

public class RegisterPresenter implements RegisterInterface.RegisterPresenter {

    RegisterInterface.RegisterView mRegisterView;
    RegisterInterface.RegisterInteractor mRegisterInteractor;

    RegisterPresenter(RegisterInterface.RegisterView registerView, RegisterInterface.RegisterInteractor registerInteractor) {
        mRegisterView = registerView;
        mRegisterInteractor = registerInteractor;
    }

    @Override
    public View addPhone(View view, LinearLayout linearLayout) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.register_phone, null);
        linearLayout.addView(rowView, linearLayout.getChildCount() - 1);
        return rowView;
    }

    @Override
    public void deletePhone(View view, LinearLayout linearLayout) {
        linearLayout.removeView((View) view.getParent());
    }

    @Override
    public void createNewUser( String email, String password, String name, String Age, String caregive_phone, String patient_phone) {
        boolean result = mRegisterInteractor.authenticateNewUser( email, password);
        if (result) {
            //show message success
            mRegisterView.showMessage("Registration successful!");
            // hide the progress bar

            User user = new User( name, email, Age, caregive_phone, patient_phone);
            registerNewUser(user);

        } else {
            // Registration failed
            mRegisterView.showMessage("Registration failed!!" + " Please try again later");

            // hide the progress bar
        }
    }


    @Override
    public void registerNewUser(User user) {
        mRegisterInteractor.registerNewUser(user);
        mRegisterView.startMedicineListActivity();
    }
}
