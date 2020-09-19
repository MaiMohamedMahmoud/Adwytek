package com.marscode.pwn.adwytek.Screens.SignIn;

import android.util.Log;

public class LoginPresenter implements LoginInterface.LoginPresenter {

    LoginInteractor loginInteractor;
    LoginInterface.LoginView loginView;

    LoginPresenter(LoginInteractor loginInteractor, LoginInterface.LoginView loginView) {
        this.loginInteractor = loginInteractor;
        this.loginView = loginView;
    }

    @Override
    public void signIn(String email, String password) {
        boolean result;
        result = loginInteractor.signIn(email, password);
        if (result) {
            loginView.startMedicineListActivity();
            loginView.showMessage("Successfully Logged In");
        } else {
            loginView.showMessage("Login Failed");
        }
    }
}
