package com.marscode.pwn.adwytek.Screens.SignIn;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.marscode.pwn.adwytek.R;

public class LoginPresenter implements LoginInterface.LoginPresenter, LoginInterface.LoginInteractor.OnFinishedListener {

    LoginInteractor loginInteractor;
    LoginInterface.LoginView loginView;
    Context context;

    LoginPresenter(LoginInteractor loginInteractor, LoginInterface.LoginView loginView, Context context) {
        this.loginInteractor = loginInteractor;
        this.loginView = loginView;
        this.context = context;
    }

    @Override
    public void signIn(String email, String password) {
        loginInteractor.signIn(email, password, this);
    }

    @Override
    public void onFinished(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            loginView.startMedicineListActivity();
            loginView.showMessage(context.getString(R.string.successLogin));
        } else {
            loginView.showMessage(context.getString(R.string.login_failed));
        }
    }
}
