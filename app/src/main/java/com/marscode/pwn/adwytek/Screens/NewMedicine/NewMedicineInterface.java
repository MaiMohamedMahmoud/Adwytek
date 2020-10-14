package com.marscode.pwn.adwytek.Screens.NewMedicine;

import android.view.View;
import android.widget.LinearLayout;

interface NewMedicineInterface {


    interface NewMedicineInteractor {

    }

    interface NewMedicinePresenter {
        View addDosePerTime(View view, LinearLayout linearLayout);
    }

    interface NewMedicineView {

    }
}
