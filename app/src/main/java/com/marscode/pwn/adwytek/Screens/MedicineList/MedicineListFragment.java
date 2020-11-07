package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import androidx.activity.OnBackPressedCallback;
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
    private ProgressDialog dialog;
    private ImageView no_medicine;

    boolean doubleBackToExitPressedOnce;


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
        doubleBackToExitPressedOnce = false;
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                //if the user click the back button two times quit the app.
                if (doubleBackToExitPressedOnce) {
                    getActivity().finishAffinity();
                }

                doubleBackToExitPressedOnce = true;
                Toast.makeText(getActivity(), getString(R.string.back_click), Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce=false;
                    }
                }, 2000);
            }
        };
        getActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public void showDialog() {
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.loading_message));
        dialog.show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicine_list, container, false);
        showDialog();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerMedicine = view.findViewById(R.id.recycle_medicineList);
        fab_new_medicine = view.findViewById(R.id.fab_new_medicine);
        no_medicine = view.findViewById(R.id.no_medicine);
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
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (medicineList.size() > 0) {
            recyclerMedicine.setVisibility(View.VISIBLE);
            no_medicine.setVisibility(View.GONE);
            MedicineListAdapter medicinelistAdapter = new MedicineListAdapter(medicineList);
            recyclerMedicine.setAdapter(medicinelistAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerMedicine.setLayoutManager(linearLayoutManager);

            medicineListPresenter.setListMedicineSharedPreference(medicineList);
        } else {
            recyclerMedicine.setVisibility(View.GONE);
            no_medicine.setVisibility(View.VISIBLE);
        }
    }
}
