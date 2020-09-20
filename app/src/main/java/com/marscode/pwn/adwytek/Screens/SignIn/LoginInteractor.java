package com.marscode.pwn.adwytek.Screens.SignIn;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.marscode.pwn.adwytek.R;

import androidx.annotation.NonNull;

public class LoginInteractor implements LoginInterface.LoginInteractor {
    FirebaseAuth auth;

    LoginInteractor(FirebaseAuth auth) {
        this.auth = auth;
    }

    @Override
    public void signIn(String email, String password, final LoginInteractor.OnFinishedListener listener) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                listener.onFinished(task);
            }

        });
    }
}
