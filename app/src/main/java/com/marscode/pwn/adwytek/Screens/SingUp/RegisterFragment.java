package com.marscode.pwn.adwytek.Screens.SingUp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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

import java.util.ArrayList;
import java.util.List;

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
    EditText editTxtName;
    EditText editTxtAge;
    EditText editTxtPatientPhone;
    EditText editTxtCareGiverPhone;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    List<String> phoneList;
    List<String> caregiverList;

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
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Log.i("yarab ",currentUser.getUid());
            startMedicineListActivity();
        }
    }

    @VisibleForTesting
    @NonNull
    public static RegisterFragment getInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        registerPresenter = new RegisterPresenter(this, new RegisterInteractor(mAuth, mDatabase), getContext());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        phoneContainer = view.findViewById(R.id.phone_container);
        caregiverPhoneContainer = view.findViewById(R.id.caregiver_phone_container);
        txtSignIn = view.findViewById(R.id.txt_sign_in);
        Button addPhone = view.findViewById(R.id.add_button);
        Button addCareGiverPhone = view.findViewById(R.id.add_caregiver_button);
        Button signUp = view.findViewById(R.id.btn_sign_up);
        editTxtEmail = view.findViewById(R.id.edit_txt_email);
        editTxtPassword = view.findViewById(R.id.edit_txt_password);
        editTxtName = view.findViewById(R.id.edit_txt_name);
        editTxtAge = view.findViewById(R.id.edit_txt_Age);
        phoneList = new ArrayList<>();
        caregiverList = new ArrayList<>();

        AddPhone(view);
        AddCareGiverPhone(view);
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
                startLoggedInActivity();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTxtCareGiverPhone.clearFocus();
                editTxtPatientPhone.clearFocus();
                registerPresenter.createNewUser(editTxtEmail.getText().toString(), editTxtPassword.getText().toString(), editTxtName.getText().toString(), editTxtAge.getText().toString(), caregiverList, phoneList);
            }
        });
        return view;
    }


    public void AddPhone(View view) {

        phoneView = registerPresenter.addPhone(view, phoneContainer);

        int childCount = phoneContainer.getChildCount() - 1;
        for (int i = 0; i < childCount; i++) {
            deletePhone = phoneView.findViewById(R.id.delete_button);
            editTxtPatientPhone = phoneView.findViewById(R.id.edit_txt_phone);
            editTxtPatientPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    String input;
                    EditText editText;
                    if (!hasFocus) {
                        editText = (EditText) view;
                        input = editText.getText().toString();
                        phoneList.add(input);
                    } else {
                        editText = (EditText) view;
                        input = editText.getText().toString();
                        if (phoneList.contains(input)) {
                            phoneList.remove(input);
                        }
                    }
                }
            });

            deletePhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View v = (View) view.getParent();
                    EditText editText;
                    editText = v.findViewById(R.id.edit_txt_phone);
                    if (phoneList.contains(editText.getText().toString())) {
                        phoneList.remove(editText.getText().toString());
                    }
                    registerPresenter.deletePhone(view, phoneContainer);

                }
            });
        }

    }

    public void AddCareGiverPhone(View view) {
        caregiverPhoneView = registerPresenter.addPhone(view, caregiverPhoneContainer);
        int childCount = caregiverPhoneContainer.getChildCount() - 1;
        for (int i = 0; i < childCount; i++) {
            deletePhone = caregiverPhoneView.findViewById(R.id.delete_button);
            editTxtCareGiverPhone = caregiverPhoneView.findViewById(R.id.edit_txt_phone);
            editTxtCareGiverPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    String input;
                    EditText editText;
                    if (!hasFocus) {
                        editText = (EditText) view;
                        input = editText.getText().toString();
                        caregiverList.add(input);
                    } else {
                        editText = (EditText) view;
                        input = editText.getText().toString();
                        if (caregiverList.contains(input)) {
                            caregiverList.remove(input);
                        }
                    }
                }
            });
            deletePhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View v = (View) view.getParent();
                    EditText editText;
                    editText = v.findViewById(R.id.edit_txt_phone);
                    if (caregiverList.contains(editText.getText().toString())) {
                        caregiverList.remove(editText.getText().toString());
                    }
                    registerPresenter.deletePhone(view, caregiverPhoneContainer);
                }
            });
        }
    }

    @Override
    public void startLoggedInActivity() {
        /**
         * here open the LoginActivity
         */
        Intent intent = new Intent(getContext(), LoginActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void validateUser() {

        //editTxtName.getText().toString(), editTxtPassword.getText().toString(),editTxtAge.getText().toString(),editTxtCareGiverPhone.getText().toString(),editTxtPatientPhone.getText().toString()

        // Validations for input email and password
        if (TextUtils.isEmpty(editTxtEmail.getText().toString())) {
            editTxtEmail.setError("Please enter email!!");
            editTxtEmail.setFocusable(true);
            return;
        }
        if (TextUtils.isEmpty(editTxtPassword.getText().toString())) {
            editTxtPassword.setError("Please enter password!!");
            editTxtPassword.setFocusable(true);
            return;
        }
    }

    @Override
    public void startMedicineListActivity() {
        /**
         * here open the MedicineListActivity
         */
        Intent intent = new Intent(getContext(), MedicineListActivity.class);
        getActivity().startActivity(intent);

    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(
                getActivity().getApplicationContext(),
                message,
                Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

}