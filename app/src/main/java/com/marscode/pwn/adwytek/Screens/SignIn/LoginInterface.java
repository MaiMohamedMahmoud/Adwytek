package com.marscode.pwn.adwytek.Screens.SignIn;

import com.marscode.pwn.adwytek.Model.User;

interface LoginInterface {

    interface LoginInteractor {
        boolean signIn(String email, String password);
    }

    interface LoginPresenter {
        void signIn(String email, String password);
    }

    interface LoginView {
        void validateUser();

        void startMedicineListActivity();

        void showMessage(String message);

        void showLoading();

        void hideLoading();
    }
}
