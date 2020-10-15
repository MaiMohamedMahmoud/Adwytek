package com.marscode.pwn.adwytek.Screens.NewMedicine;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.FirebaseDatabase;
import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.SingUp.RegisterInteractor;
import com.marscode.pwn.adwytek.Screens.SingUp.RegisterPresenter;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewMedicineFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    LinearLayout medication_dose_data;
    View doseView;
    NewMedicinePresenter newMedicinePresenter;
    TextView txt_dose_time;
    Button start_btn;
    Button end_btn;
    Button btn_mon;
    Button btn_tue;
    Button btn_wen;
    Button btn_thu;
    Button btn_fri;
    Button btn_sat;
    Button btn_sun;
    private int mYear, mMonth, mDay, mHour, mMinute;
    List<String> timeList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_medicine, container, false);
        medication_dose_data = view.findViewById(R.id.dose_container);
        start_btn = view.findViewById(R.id.btn_start_date);
        end_btn = view.findViewById(R.id.btn_end_date);
        btn_mon = view.findViewById(R.id.btn_mon);
        btn_tue = view.findViewById(R.id.btn_tue);
        btn_wen = view.findViewById(R.id.btn_wen);
        btn_thu = view.findViewById(R.id.btn_thu);
        btn_fri = view.findViewById(R.id.btn_fri);
        btn_sat = view.findViewById(R.id.btn_sat);
        btn_sun = view.findViewById(R.id.btn_sun);
        newMedicinePresenter = new NewMedicinePresenter();
        timeList = new ArrayList<>();
        Spinner spinner_frequency_time = view.findViewById(R.id.frequency_time);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_frequency_time = ArrayAdapter.createFromResource(getContext(),
                R.array.frequency_time, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_frequency_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_frequency_time.setAdapter(adapter_frequency_time);
        spinner_frequency_time.setOnItemSelectedListener(this);

        start_btn.setOnClickListener(this);
        end_btn.setOnClickListener(this);
        btn_mon.setOnClickListener(this);
        btn_tue.setOnClickListener(this);
        btn_wen.setOnClickListener(this);
        btn_thu.setOnClickListener(this);
        btn_fri.setOnClickListener(this);
        btn_sat.setOnClickListener(this);
        btn_sun.setOnClickListener(this);

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

        int childCount = medication_dose_data.getChildCount() - 1;
        for (int i = 0; i <= childCount; i++) {
            txt_dose_time = doseView.findViewById(R.id.dose_time);
            txt_dose_time.setOnClickListener(this);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getCurrentDate() {
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

    }

    public void getCurrentTime() {
        final Calendar cal = Calendar.getInstance();
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);
    }

    public void showDatePickerDialog(final Button buttonView) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        final Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        calendar.set(year, monthOfYear, dayOfMonth);
                        Date date_picker = calendar.getTime();
                        String dateString = sdf.format(date_picker);
                        buttonView.setText(dateString);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void showTimePickerDialog(final TextView txt) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txt.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_start_date:
                // Get Current Date
                getCurrentDate();
                //Launch Date Picker Dialog
                showDatePickerDialog(start_btn);

                break;
            case R.id.btn_end_date:
                // Get Current Date
                getCurrentDate();
                //Launch Date Picker Dialog
                showDatePickerDialog(end_btn);
                break;

            case R.id.btn_mon:
                btn_mon.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));;
                break;
            case R.id.btn_tue:
                btn_tue.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));;
                break;
            case R.id.btn_wen:
                btn_wen.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));;
                break;
            case R.id.btn_thu:
                btn_thu.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));;
                break;
            case R.id.btn_fri:
                btn_fri.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));;
                break;
            case R.id.btn_sat:
                btn_sat.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));;
                break;
            case R.id.btn_sun:
                btn_sun.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));;
                break;
            default:
                // Get Current Time
                getCurrentTime();
                // Launch Time Picker Dialog
                Log.i("TextView", view.getId() + "");
                showTimePickerDialog((TextView) view);
        }


    }

}
