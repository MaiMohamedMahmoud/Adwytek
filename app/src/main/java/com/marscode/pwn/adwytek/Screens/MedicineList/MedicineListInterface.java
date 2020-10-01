package com.marscode.pwn.adwytek.Screens.MedicineList;

import com.marscode.pwn.adwytek.Model.Medicine;

import java.util.List;

interface MedicineListInterface {

    interface MedicineListInteractor {
        List<Medicine> getMedicineList();

        void addNewMedicine();
    }

    interface MedicineListView {
        void setMedicineList(List<Medicine> medicineList);
    }

    interface MedicineListPresenter {
        List<Medicine> getMedicineList();

        void addNewMedicine();
    }

}
