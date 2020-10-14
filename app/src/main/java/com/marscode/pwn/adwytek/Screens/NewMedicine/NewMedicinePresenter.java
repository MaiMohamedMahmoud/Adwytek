package com.marscode.pwn.adwytek.Screens.NewMedicine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.marscode.pwn.adwytek.R;

public class NewMedicinePresenter implements NewMedicineInterface.NewMedicinePresenter {

    NewMedicinePresenter(){

    }

    @Override
    public View addDosePerTime(View view, LinearLayout linearLayout) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView =   inflater.inflate(R.layout.dose, null);
        linearLayout.addView(rowView, linearLayout.getChildCount() - 1);
        return rowView;

    }
}
