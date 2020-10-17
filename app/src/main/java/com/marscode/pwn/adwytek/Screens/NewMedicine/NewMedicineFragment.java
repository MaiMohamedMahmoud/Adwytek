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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marscode.pwn.adwytek.Model.Dose;
import com.marscode.pwn.adwytek.Model.Medicine;
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

public class NewMedicineFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, View.OnFocusChangeListener {
    LinearLayout medication_dose_data;
    View doseView;
    NewMedicinePresenter newMedicinePresenter;
    NewMedicineInteractor newMedicineInteractor;
    TextView txt_dose_time;
    EditText txt_dose_quantity;
    EditText edit_txt_medicine_name;
    Button start_btn;
    Button end_btn;
    Button btn_mon, btn_tue, btn_wen, btn_thu, btn_fri, btn_sat, btn_sun;
    Date startDate, endDate, date_picker;
    private int mYear, mMonth, mDay, mHour, mMinute;
    List<String> timeList;
    List<String> daysList;
    List<Dose> doseList;
    List<String> doseTimeList;
    List<String> doseQuantityList;
    long frequency_of_intake;
    Button addMedicineBtn;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_medicine, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        medication_dose_data = view.findViewById(R.id.dose_container);
        start_btn = view.findViewById(R.id.btn_start_date);
        end_btn = view.findViewById(R.id.btn_end_date);
        addMedicineBtn = view.findViewById(R.id.save);
        edit_txt_medicine_name = view.findViewById(R.id.edit_txt_medicine_name);
        daysList = new ArrayList<>();
        doseList = new ArrayList<>();
        btn_mon = view.findViewById(R.id.btn_mon);
        btn_tue = view.findViewById(R.id.btn_tue);
        btn_wen = view.findViewById(R.id.btn_wen);
        btn_thu = view.findViewById(R.id.btn_thu);
        btn_fri = view.findViewById(R.id.btn_fri);
        btn_sat = view.findViewById(R.id.btn_sat);
        btn_sun = view.findViewById(R.id.btn_sun);
        newMedicineInteractor = new NewMedicineInteractor(mAuth, mDatabase);
        newMedicinePresenter = new NewMedicinePresenter(newMedicineInteractor);
        timeList = new ArrayList<>();
        doseTimeList = new ArrayList<>();
        doseQuantityList = new ArrayList<>();
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
        addMedicineBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        Log.i("yaraaaaab yaraaab", adapterView.getItemAtPosition(pos) + " " + l);
        medication_dose_data.removeAllViewsInLayout();
        for (int i = 0; i <= l; i++) {
            frequency_of_intake = l;
            addDosePerTime(view);
        }
    }

    public void addDosePerTime(View view) {
        doseView = newMedicinePresenter.addDosePerTime(view, medication_dose_data);

        int childCount = medication_dose_data.getChildCount() - 1;
        for (int i = 0; i <= childCount; i++) {
            txt_dose_time = doseView.findViewById(R.id.dose_time);
            txt_dose_quantity = doseView.findViewById(R.id.dose_quantity);
            txt_dose_time.setOnClickListener(this);
            txt_dose_quantity.setOnFocusChangeListener(this);
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
                        date_picker = calendar.getTime();
                        Log.i("yarab ", "inside" + date_picker);
                        String dateString = sdf.format(date_picker);
                        buttonView.setText(dateString);
                        if (buttonView == start_btn) {
                            startDate = date_picker;
                        }
                        if (buttonView == end_btn) {
                            endDate = date_picker;
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void showTimePickerDialog(final TextView txt) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String dateString = hourOfDay + ":" + minute;
                        txt.setText(dateString);

                        doseTimeList.add(dateString);
                        Log.i("yarab", dateString.toString() + "time pich" + dateString);
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    public void addNewMedicine() {


     /*   Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        Dose dose1 = new Dose("Su", c, "2");
        Dose dose2 = new Dose("Tu", c, "3");
*/

        for (int days = 0; days < daysList.size(); days++) {
            for (int dose = 0; dose <= frequency_of_intake; dose++) {
                Log.i("yarab inside loop", frequency_of_intake + "dose time" + doseTimeList.get(dose) + doseQuantityList.get(dose));
                Dose doseObj = new Dose(daysList.get(days),
                        doseTimeList.get(dose),
                        doseQuantityList.get(dose));
                doseList.add(doseObj);
            }
        }
        /*doseList.add(dose1);
        doseList.add(dose2);*/

        Medicine medicineOmega = new Medicine(edit_txt_medicine_name.getText().toString(), startDate, endDate, frequency_of_intake + 1 + "", doseList);

        Log.i("yarab", edit_txt_medicine_name.getText().toString() + "start " + startDate + "End " + endDate + "Freq " + frequency_of_intake + "dose " + doseList.size());
        newMedicinePresenter.addNewMedicine(medicineOmega);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_start_date:
                // Get Current Date
                getCurrentDate();
                //Launch Date Picker Dialog
                showDatePickerDialog(start_btn);
                Log.i("yarab ", startDate + "date picker " + date_picker);
                break;
            case R.id.btn_end_date:
                // Get Current Date
                getCurrentDate();
                //Launch Date Picker Dialog
                showDatePickerDialog(end_btn);
                Log.i("yarab ", endDate + "date picker " + date_picker);
                break;

            case R.id.btn_mon:
                btn_mon.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));
                ;
                daysList.add("Mo");
                break;
            case R.id.btn_tue:
                btn_tue.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));
                ;
                daysList.add("Tue");
                break;
            case R.id.btn_wen:
                btn_wen.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));
                ;
                daysList.add("Wen");
                break;
            case R.id.btn_thu:
                btn_thu.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));
                ;
                daysList.add("Thu");
                break;
            case R.id.btn_fri:
                btn_fri.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));
                ;
                daysList.add("Fr");
                break;
            case R.id.btn_sat:
                btn_sat.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));
                ;
                daysList.add("Sa");
                break;
            case R.id.btn_sun:
                btn_sun.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));
                ;
                daysList.add("Su");
                break;

            case R.id.save:
                addNewMedicine();
                break;
            default:
                // Get Current Time
                getCurrentTime();
                // Launch Time Picker Dialog
                Log.i("TextView", view.getId() + "");
                showTimePickerDialog((TextView) view);
        }


    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        String input;
        EditText editText;
        if (!hasFocus) {
            editText = (EditText) view;
            input = editText.getText().toString();
            doseQuantityList.add(input);
        } else {
            editText = (EditText) view;
            input = editText.getText().toString();
            if (doseQuantityList.contains(input)) {
                doseQuantityList.remove(input);
            }
        }
    }
}
