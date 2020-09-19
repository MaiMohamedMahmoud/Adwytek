package com.marscode.pwn.adwytek.Screens.SingUp;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marscode.pwn.adwytek.Model.User;

import androidx.annotation.NonNull;

public class RegisterInteractor implements RegisterInterface.RegisterInteractor {
    boolean result = false;
    FirebaseAuth auth;
    DatabaseReference database;

    RegisterInteractor(FirebaseAuth auth, DatabaseReference database) {
        this.auth = auth;
        this.database = database;
    }

    @Override
    public boolean authenticateNewUser(String email, String password) {
        // create new user or register new user
        auth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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

    @Override
    public void registerNewUser(User user) {
        user.uid = auth.getUid();
        database.child("users").child(user.uid).setValue(user.toMap());
    }
}
