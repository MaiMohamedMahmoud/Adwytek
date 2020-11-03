package com.marscode.pwn.adwytek.Screens.SignIn;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.firebase.auth.FirebaseAuth;
import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.MedicineList.MedicineListActivity;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements LoginInterface.LoginView {

    FirebaseAuth mAuth;
    EditText editTxtEmail;
    EditText editTxtPassword;
    Button signIn;
    LoginInteractor loginInteractor;
    private AwesomeValidation mAwesomeValidation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        final LoginPresenter loginPresenter = new LoginPresenter(new LoginInteractor(mAuth), this, getActivity().getApplicationContext());
        editTxtEmail = view.findViewById(R.id.edit_txt_email);
        editTxtPassword = view.findViewById(R.id.edit_txt_password);
        mAwesomeValidation = new AwesomeValidation(BASIC);

        signIn = view.findViewById(R.id.btn_login);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateUser()) {
                    loginPresenter.signIn(editTxtEmail.getText().toString(), editTxtPassword.getText().toString());
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean validateUser() {
        // to validate email
        mAwesomeValidation.addValidation(getActivity(), R.id.login_email_text_input_layout, Patterns.EMAIL_ADDRESS, R.string.err_email);
        // to validate password
        String regexPassword = "(?=.*[a-zA-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        mAwesomeValidation.addValidation(getActivity(), R.id.login_password_text_input_layout, regexPassword, R.string.err_password);
        return mAwesomeValidation.validate();
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