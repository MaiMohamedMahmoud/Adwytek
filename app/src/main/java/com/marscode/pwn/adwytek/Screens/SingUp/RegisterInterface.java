package com.marscode.pwn.adwytek.Screens.SingUp;

import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.marscode.pwn.adwytek.Model.User;

interface RegisterInterface {

    interface RegisterInteractor {

        boolean authenticateNewUser( String email, String password);

        void registerNewUser(User user);
    }

    interface RegisterPresenter {

        View addPhone(View view, LinearLayout linearLayout);

        void deletePhone(View view, LinearLayout linearLayout);

        void createNewUser( String email, String password, String name, String Age, String caregive_phone, String patient_phone);

        void registerNewUser(User user);

    }

    interface RegisterView {
        void startLoggedInActivity();

        void validateUser();

        void startMedicineListActivity();

        void showMessage(String message);

        void showLoading();

        void hideLoading();
    }
}
