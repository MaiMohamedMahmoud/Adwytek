package com.marscode.pwn.adwytek.Screens.SignIn;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.marscode.pwn.adwytek.Model.User;

interface LoginInterface {

    interface LoginInteractor {
        interface OnFinishedListener {
            void onFinished(Task<AuthResult> task);
        }

        void signIn(String email, String password, LoginInteractor.OnFinishedListener listener);
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
