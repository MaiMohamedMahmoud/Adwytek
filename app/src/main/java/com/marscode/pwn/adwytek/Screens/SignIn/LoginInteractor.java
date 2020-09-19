package com.marscode.pwn.adwytek.Screens.SignIn;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;

public class LoginInteractor implements LoginInterface.LoginInteractor {
    FirebaseAuth auth;
    boolean result;

    LoginInteractor(FirebaseAuth auth) {
        this.auth = auth;
    }

    @Override
    public boolean signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    result = true;
                } else {
                    result = false;
                }
            }

        });
        return result;
    }
}
