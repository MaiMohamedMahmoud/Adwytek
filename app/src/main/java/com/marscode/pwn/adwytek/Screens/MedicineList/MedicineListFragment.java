package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marscode.pwn.adwytek.Model.Medicine;
import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.NewMedicine.NewMedicineActivity;
import com.marscode.pwn.adwytek.Screens.SignIn.LoginActivity;
import com.marscode.pwn.adwytek.Screens.Widget.MedicineWidgetService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MedicineListFragment extends Fragment implements MedicineListInterface.MedicineListView {

    RecyclerView recyclerMedicine;
    MedicineListPresenter medicineListPresenter;
    FirebaseAuth mAuth;
    FloatingActionButton fab_new_medicine;
    DatabaseReference mDatabase;


    @Override
    public void onStop() {
        super.onStop();

        //sign out on the profile page
        //mAuth.signOut();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicine_list, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerMedicine = view.findViewById(R.id.recycle_medicineList);
        fab_new_medicine = view.findViewById(R.id.fab_new_medicine);
        fab_new_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewMedicine();
            }
        });
        medicineListPresenter = new MedicineListPresenter(getContext(), this, new MedicineListInteractor(mAuth, mDatabase));
        medicineListPresenter.getMedicineList();
        return view;
    }

    public void addNewMedicine() {
        //medicineListPresenter.addNewMedicine();
        startNewMedicineActivity();

    }

    @Override
    public void startNewMedicineActivity() {
        /*
         * here open the New medicine
         */
        Intent intent = new Intent(getContext(), NewMedicineActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void setMedicineList(List<Medicine> medicineList) {
        Log.i("yarab inside fragment", medicineList.size() + "");
        MedicineListAdapter medicinelistAdapter = new MedicineListAdapter(medicineList);
        recyclerMedicine.setAdapter(medicinelistAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerMedicine.setLayoutManager(linearLayoutManager);

        medicineListPresenter.setListMedicineSharedPreference(medicineList);
        //medicineListPresenter.setAlarm(getContext(), medicineList);

    }
}
