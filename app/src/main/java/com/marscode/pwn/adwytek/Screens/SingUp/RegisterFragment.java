package com.marscode.pwn.adwytek.Screens.SingUp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.SignIn.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements RegisterInterface.RegisterView {

    LinearLayout phoneContainer;
    RegisterPresenter registerPresenter;
    View phoneView;
    Button deletePhone;
    TextView txtSignIn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        txtSignIn = view.findViewById(R.id.txt_sign_in);
        registerPresenter = new RegisterPresenter(this);
        Button addPhone = view.findViewById(R.id.add_button);
        Button signUp = view.findViewById(R.id.btn_sign_up);
        addPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPhone(view);
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
                medicineListActivity();
            }
        });
        return view;
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
        Intent intent = new Intent(getContext(), LoginActivity.class);
        getActivity().startActivity(intent);

    }
}