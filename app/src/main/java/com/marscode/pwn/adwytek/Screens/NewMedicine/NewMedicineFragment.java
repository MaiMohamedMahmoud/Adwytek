package com.marscode.pwn.adwytek.Screens.NewMedicine;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.firebase.database.FirebaseDatabase;
import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.SingUp.RegisterInteractor;
import com.marscode.pwn.adwytek.Screens.SingUp.RegisterPresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewMedicineFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    LinearLayout medication_dose_data;
    View doseView;
    NewMedicinePresenter newMedicinePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_medicine, container, false);
        medication_dose_data = view.findViewById(R.id.dose_container);
        newMedicinePresenter = new NewMedicinePresenter();

        Spinner spinner_frequency_time = view.findViewById(R.id.frequency_time);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_frequency_time = ArrayAdapter.createFromResource(getContext(),
                R.array.frequency_time, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_frequency_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_frequency_time.setAdapter(adapter_frequency_time);
        spinner_frequency_time.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        Log.i("yaraaaaab yaraaab", adapterView.getItemAtPosition(pos) + " " + l);
        medication_dose_data.removeAllViewsInLayout();
        for (int i = 0; i <= l; i++) {
            addDosePerTime(view);
        }
    }

    public void addDosePerTime(View view) {
        doseView = newMedicinePresenter.addDosePerTime(view, medication_dose_data);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
