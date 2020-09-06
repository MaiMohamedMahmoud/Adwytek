package com.marscode.pwn.adwytek.Screens.SingUp;

import android.view.View;
import android.widget.LinearLayout;

import com.marscode.pwn.adwytek.Model.User;

interface RegisterInterface {

    interface RegisterInteractor {
        void addUser(User user);
    }

    interface RegisterPresenter {

        View addPhone(View view, LinearLayout linearLayout);

        void deletePhone(View view, LinearLayout linearLayout);
    }

    interface RegisterView {
        void loggedIn();
        void medicineListActivity();
    }
}
