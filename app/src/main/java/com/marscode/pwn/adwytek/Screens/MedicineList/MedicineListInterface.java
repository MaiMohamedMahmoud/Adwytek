package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.marscode.pwn.adwytek.Model.Dose;
import com.marscode.pwn.adwytek.Model.Medicine;

import java.util.Calendar;
import java.util.List;

interface MedicineListInterface {

    interface MedicineListInteractor {
        interface OnFinishedListener {
            void onFinished(DataSnapshot dataSnapshot);
        }

        void getMedicineListByUserId(OnFinishedListener listener);

        void setAlarm(Context context, Calendar calendar,int AlarmId);
    }

    interface MedicineListView {
        void startNewMedicineActivity();

        void setMedicineList(List<Medicine> medicineList);
    }

    interface MedicineListPresenter {
        void getMedicineList();
        void setListMedicineSharedPreference(List<Medicine> medicineList);
        void setAlarm(Context context, List<Medicine> medicineList);

    }

}
