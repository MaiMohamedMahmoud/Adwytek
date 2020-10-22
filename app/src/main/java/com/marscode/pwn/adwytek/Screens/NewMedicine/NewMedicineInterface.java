package com.marscode.pwn.adwytek.Screens.NewMedicine;

import android.view.View;
import android.widget.LinearLayout;

import com.marscode.pwn.adwytek.Model.Dose;
import com.marscode.pwn.adwytek.Model.Medicine;

import java.util.List;

interface NewMedicineInterface {


    interface NewMedicineInteractor {
        void addNewMedicine(Medicine medicine, List<Dose> doseList);
    }

    interface NewMedicinePresenter {
        View addDosePerTime(View view, LinearLayout linearLayout);

        void addNewMedicine(Medicine medicine, List<Dose> doseList);
    }

    interface NewMedicineView {

    }
}
