package com.marscode.pwn.adwytek.Screens.SingUp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.marscode.pwn.adwytek.Model.User;
import com.marscode.pwn.adwytek.R;

import java.util.List;

public class RegisterPresenter implements RegisterInterface.RegisterPresenter, RegisterInterface.RegisterInteractor.OnFinishedListener {

    RegisterInterface.RegisterView mRegisterView;
    RegisterInterface.RegisterInteractor mRegisterInteractor;
    Context context;
    User user;

    RegisterPresenter(RegisterInterface.RegisterView registerView, RegisterInterface.RegisterInteractor registerInteractor, Context context) {
        mRegisterView = registerView;
        mRegisterInteractor = registerInteractor;
        this.context = context;
    }

    @Override
    public View addPhone(View view, LinearLayout linearLayout) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.register_phone, null);
        linearLayout.addView(rowView, linearLayout.getChildCount() - 1);
        return rowView;

    }

    @Override
    public void deletePhone(View view, LinearLayout linearLayout) {
        linearLayout.removeView((View) view.getParent());
    }

    @Override
    public void createNewUser(String email, String password, String name, String Age, List<String> caregive_phone, List<String> patient_phone) {
        mRegisterInteractor.authenticateNewUser(email, password, this);
        user = new User(name, email, Age, caregive_phone, patient_phone);


    }


    @Override
    public void registerNewUser(User user) {
        mRegisterInteractor.registerNewUser(user);
        mRegisterView.startMedicineListActivity();
    }

    @Override
    public void onFinished(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            //show message success
            mRegisterView.showMessage(context.getString(R.string.success_register));
            // hide the progress bar
            registerNewUser(user);
        } else {
            // Registration failed
            mRegisterView.showMessage(context.getString(R.string.register_fail));
            // hide the progress bar
        }
    }
}
