package com.marscode.pwn.adwytek.Screens.NewMedicine;

import android.view.View;
import android.widget.LinearLayout;

import com.marscode.pwn.adwytek.Model.Medicine;

interface NewMedicineInterface {


    interface NewMedicineInteractor {
        void addNewMedicine(Medicine medicine);
    }

    interface NewMedicinePresenter {
        View addDosePerTime(View view, LinearLayout linearLayout);
        void addNewMedicine(Medicine medicine);
    }

    interface NewMedicineView {

    }
}
