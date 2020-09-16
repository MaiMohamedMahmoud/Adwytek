package com.marscode.pwn.adwytek.Screens.SingUp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marscode.pwn.adwytek.Model.User;
import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.MedicineList.MedicineListActivity;
import com.marscode.pwn.adwytek.Screens.SignIn.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements RegisterInterface.RegisterView {

    LinearLayout phoneContainer;
    LinearLayout caregiverPhoneContainer;
    RegisterPresenter registerPresenter;
    View phoneView;
    View caregiverPhoneView;
    Button deletePhone;
    TextView txtSignIn;
    EditText editTxtEmail;
    EditText editTxtPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @VisibleForTesting
    @NonNull
    public static RegisterFragment getInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        phoneContainer = view.findViewById(R.id.phone_container);
        caregiverPhoneContainer = view.findViewById(R.id.caregiver_phone_container);
        txtSignIn = view.findViewById(R.id.txt_sign_in);
        registerPresenter = new RegisterPresenter(this);
        Button addPhone = view.findViewById(R.id.add_button);
        Button addCareGiverPhone = view.findViewById(R.id.add_caregiver_button);
        Button signUp = view.findViewById(R.id.btn_sign_up);
        editTxtEmail = view.findViewById(R.id.edit_txt_email);
        editTxtPassword = view.findViewById(R.id.edit_txt_password);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        addPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPhone(view);
            }
        });

        addCareGiverPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCareGiverPhone(view);
            }
        });
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loggedIn();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser(editTxtEmail.getText().toString(), editTxtPassword.getText().toString());

            }
        });
        return view;
    }

    private void registerNewUser(String email, String password) {
        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            editTxtEmail.setError("Please enter email!!");
            editTxtEmail.setFocusable(true);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTxtPassword.setError("Please enter password!!");
            editTxtPassword.setFocusable(true);
            return;
        }

        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar

                            writeNewUser(mAuth.getUid(),"Mai","Email");

                        } else {
                            // Registration failed
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar

                        }
                    }
                });
    }

    public void AddPhone(View view) {
        phoneView = registerPresenter.addPhone(view, phoneContainer);
        deletePhone = phoneView.findViewById(R.id.delete_button);
        deletePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPresenter.deletePhone(view, phoneContainer);
            }
        });
    }

    public void AddCareGiverPhone(View view) {
        caregiverPhoneView = registerPresenter.addPhone(view,caregiverPhoneContainer);
        deletePhone = caregiverPhoneView.findViewById(R.id.delete_button);
        deletePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPresenter.deletePhone(view, caregiverPhoneContainer);
            }
        });
    }


    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
        // if the user created intent to login activity
        medicineListActivity();
    }

    @Override
    public void loggedIn() {
        /**
         * here open the LoginActivity
         */
        Intent intent = new Intent(getContext(), LoginActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void medicineListActivity() {
        /**
         * here open the MedicineListActivity
         */
        Intent intent = new Intent(getContext(), MedicineListActivity.class);
        getActivity().startActivity(intent);

    }
}