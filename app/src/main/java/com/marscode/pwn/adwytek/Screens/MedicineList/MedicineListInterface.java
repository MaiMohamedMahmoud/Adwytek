package com.marscode.pwn.adwytek.Screens.MedicineList;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.marscode.pwn.adwytek.Model.Medicine;

import java.util.List;

interface MedicineListInterface {

    interface MedicineListInteractor {
        interface OnFinishedListener {
            void onFinished(DataSnapshot dataSnapshot);
        }

        void getMedicineList(OnFinishedListener listener);

        void addNewMedicine();
    }

    interface MedicineListView {
        void setMedicineList(List<Medicine> medicineList);
    }

    interface MedicineListPresenter {
        void getMedicineList();

        void addNewMedicine();
    }

}
