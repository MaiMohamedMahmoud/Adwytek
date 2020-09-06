package com.marscode.pwn.adwytek.Screens.SingUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.marscode.pwn.adwytek.Model.User;
import com.marscode.pwn.adwytek.R;

class RegisterPresenter implements RegisterInterface.RegisterPresenter {

    RegisterInterface.RegisterView mRegisterView;

    RegisterPresenter(RegisterInterface.RegisterView registerView) {
        mRegisterView = registerView;
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
}
