package com.marscode.pwn.adwytek.Screens.NewMedicine;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marscode.pwn.adwytek.Model.Dose;
import com.marscode.pwn.adwytek.Model.Medicine;
import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.SingUp.RegisterInteractor;
import com.marscode.pwn.adwytek.Screens.SingUp.RegisterPresenter;
import com.marscode.pwn.adwytek.Screens.broadcastReciever.AlarmBroadcastReceiver;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class NewMedicineFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, TextWatcher, NewMedicineInterface.NewMedicineView {
    LinearLayout medication_dose_data;
    View doseView;
    NewMedicinePresenter newMedicinePresenter;
    NewMedicineInteractor newMedicineInteractor;
    TextView txt_dose_time;
    EditText txt_dose_quantity;
    EditText edit_txt_medicine_name;
    Calendar currentDate;
    TextInputLayout dose_txt_input_layout;
    TextInputLayout medicine_name_txt_input_layout;
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
    List<Calendar> calendarList;
    long frequency_of_intake;
    Button addMedicineBtn, cancelBtn;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    AwesomeValidation mAwesomeValidation;
    boolean flag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_medicine, container, false);
        mAwesomeValidation = new AwesomeValidation(BASIC);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        flag = false;
        medication_dose_data = view.findViewById(R.id.dose_container);
        start_btn = view.findViewById(R.id.btn_start_date);
        end_btn = view.findViewById(R.id.btn_end_date);
        addMedicineBtn = view.findViewById(R.id.save);
        cancelBtn = view.findViewById(R.id.cancel);
        edit_txt_medicine_name = view.findViewById(R.id.edit_txt_medicine_name);
        medicine_name_txt_input_layout = view.findViewById(R.id.medicine_name_text_input_layout);
        daysList = new ArrayList<>();
        doseList = new ArrayList<>();
        calendarList = new ArrayList<>();
        btn_mon = view.findViewById(R.id.btn_mon);
        btn_tue = view.findViewById(R.id.btn_tue);
        btn_wen = view.findViewById(R.id.btn_wen);
        btn_thu = view.findViewById(R.id.btn_thu);
        btn_fri = view.findViewById(R.id.btn_fri);
        btn_sat = view.findViewById(R.id.btn_sat);
        btn_sun = view.findViewById(R.id.btn_sun);
        newMedicineInteractor = new NewMedicineInteractor(mAuth, mDatabase);
        newMedicinePresenter = new NewMedicinePresenter(newMedicineInteractor, this);
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        getCurrentDate();
        start_btn.setText(sdf.format(currentDate.getTime()));
        end_btn.setText(sdf.format(currentDate.getTime()));
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
        cancelBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        medication_dose_data.removeAllViewsInLayout();
        doseQuantityList.clear();
        doseTimeList.clear();
        doseList.clear();
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
            dose_txt_input_layout = doseView.findViewById(R.id.dose_quantity_text_input_layout);
            txt_dose_time.setOnClickListener(this);

        }
        dose_txt_input_layout.getEditText().addTextChangedListener(this);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public Calendar getCurrentDate() {
        //we need to define end of day to return that the end date if it will be at the same day it will be at the end hour and end min and sec of that day
        Calendar endOfDay = Calendar.getInstance();
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(mYear, mMonth, mDay);
        endOfDay.set(mYear, mMonth, mDay, 23, 59, 59);
        currentDate = cal;
        startDate = currentDate.getTime();
        endDate = endOfDay.getTime();
        return cal;
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
                        if (buttonView == end_btn) {
                            calendar.set(year, monthOfYear, dayOfMonth, 23, 59, 59);
                        } else {
                            calendar.set(year, monthOfYear, dayOfMonth);
                        }

                        if (calendar.before(currentDate)) {
                            Toast.makeText(getContext(), getString(R.string.error_date), Toast.LENGTH_LONG).show();
                        } else {
                            date_picker = calendar.getTime();
                            String dateString = sdf.format(date_picker);
                            buttonView.setText(dateString);
                            if (buttonView == start_btn) {
                                startDate = date_picker;
                                if (endDate != null) {
                                    if (endDate.before(startDate)) {
                                        endDate = startDate;
                                        end_btn.setText(sdf.format(endDate));
                                    }
                                }
                            }
                            if (buttonView == end_btn) {
                                endDate = date_picker;
                            }
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(currentDate.getTime().getTime());
        if (startDate != null && buttonView == end_btn) {
            datePickerDialog.getDatePicker().setMinDate(startDate.getTime());
        }
        datePickerDialog.show();
    }

    public void showTimePickerDialog(final TextView txt) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (txt.getText().toString() == getText(R.string.time)) {
                            String dateString = hourOfDay + ":" + minute;
                            txt.setText(dateString);
                            doseTimeList.add(dateString);
                        } else {

                            //remove previous txt before adding the new one.
                            doseTimeList.remove(txt.getText().toString());
                            String dateString = hourOfDay + ":" + minute;
                            txt.setText(dateString);
                            doseTimeList.add(dateString);
                        }
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }


    private void setAlarm(Calendar calendar) {

        int AlarmId = new Random().nextInt(Integer.MAX_VALUE);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmBroadcastReceiver.class);
        intent.putExtra("startDate", startDate.getTime());
        intent.putExtra("endDate", endDate.getTime());
        intent.putExtra("dayList", (ArrayList<String>) daysList);
        intent.putExtra("alarmID", AlarmId);
        Log.i("yarab", " before setting Alarm: "+ calendar.getTime().toString());
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(getContext(), AlarmId, intent, 0);
        if (daysList.size() > 1) {
            //if there is more than one day then the alarm need to be repeated.
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    alarmPendingIntent
            );
        } else {
            //Alarm only in one day so no need for repeating
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    alarmPendingIntent
            );
        }

    }


    public void addNewMedicine() {

        for (int days = 0; days < daysList.size(); days++) {
            for (int dose = 0; dose <= frequency_of_intake; dose++) {
                Dose doseObj = new Dose(daysList.get(days),
                        doseTimeList.get(dose),
                        doseQuantityList.get(dose));
                doseList.add(doseObj);
            }
        }
        if (endDate.before(startDate)) {
            Log.i("yarab", "resetting the value of End Date ");
            endDate = startDate;
        }
        for (int i = 0; i < doseTimeList.size(); i++) {
            Log.i("yarab", "addNewMedicine: before set Alarm "+ doseTimeList.get(i));

            //TODO fix this issue the alarm only contating the time with no info about the days.
            setAlarm(getCalendar(doseTimeList.get(i)));
            //alarmIsToday(startDate, endDate, daysList);
        }
        Medicine medicineOmega = new Medicine(edit_txt_medicine_name.getText().toString(), startDate, endDate, frequency_of_intake + 1 + "");

        newMedicinePresenter.addNewMedicine(medicineOmega, doseList);
    }

    private Calendar getCalendar(String dose) {
        String[] parts = dose.split(":");
        String hours = parts[0];
        String min = parts[1];
        Log.i("yarab dose prev", Integer.parseInt(hours) + "min" + Integer.parseInt(min) + " txt size " + doseTimeList.size());

        //remove the previous calender
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours));
        calendar.set(Calendar.MINUTE, Integer.parseInt(min));
        calendar.set(Calendar.SECOND, 0);
        //TODO you have to see this check and uncomment it.... to fix issue in alarm
        // if alarm time has already passed, increment day by 1
//        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
//            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
//        }
        return calendar;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_start_date:
                // Get Current Date
                //getCurrentDate();
                //Launch Date Picker Dialog
                showDatePickerDialog(start_btn);
                break;
            case R.id.btn_end_date:
                // Get Current Date
                //getCurrentDate();
                //Launch Date Picker Dialog
                showDatePickerDialog(end_btn);
                break;

            case R.id.btn_mon:
                btn_mon.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));
                daysList.add("Mo");
                btn_mon.setEnabled(false);
                flag = true;
                break;
            case R.id.btn_tue:
                btn_tue.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));
                ;
                daysList.add("Tue");
                btn_tue.setEnabled(false);
                flag = true;
                break;
            case R.id.btn_wen:
                btn_wen.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));
                daysList.add("Wen");
                btn_wen.setEnabled(false);
                flag = true;
                break;
            case R.id.btn_thu:
                btn_thu.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));
                daysList.add("Thu");
                btn_thu.setEnabled(false);
                flag = true;
                break;
            case R.id.btn_fri:
                btn_fri.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));
                daysList.add("Fr");
                btn_fri.setEnabled(false);
                flag = true;
                break;
            case R.id.btn_sat:
                btn_sat.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));
                daysList.add("Sa");
                btn_sat.setEnabled(false);
                flag = true;
                break;
            case R.id.btn_sun:
                btn_sun.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorxml));
                daysList.add("Su");
                btn_sun.setEnabled(false);
                flag = true;
                break;

            case R.id.save:

                boolean valid = validate();
                if (valid) {
                    addNewMedicine();
                }
                break;
            case R.id.cancel:
                closeActivity();
                break;
            default:
                // Get Current Time
                getCurrentTime();
                // Launch Time Picker Dialog
                showTimePickerDialog((TextView) view);
        }


    }

    private boolean validate() {
        mAwesomeValidation.addValidation(getActivity(), R.id.medicine_name_text_input_layout, "[a-zA-Z\\s]+", R.string.medicine_required);

        int childCount = medication_dose_data.getChildCount() - 1;
        for (int i = 0; i <= childCount; i++) {
            mAwesomeValidation.addValidation(getActivity(), R.id.dose_quantity_text_input_layout, "[0-9]", R.string.quanity_required);
            if (txt_dose_time.getText() == getString(R.string.time)) {
                Toast.makeText(getContext(), getString(R.string.dose_time_error), Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if (flag == false) {
            Toast.makeText(getContext(), getString(R.string.selectedDays), Toast.LENGTH_LONG).show();
            return flag;
        }
        if (doseTimeList.size() < frequency_of_intake + 1 || doseQuantityList.size() < frequency_of_intake + 1) {
            Toast.makeText(getContext(), getString(R.string.dose_time_error), Toast.LENGTH_LONG).show();
            return false;
        }
        return mAwesomeValidation.validate();
    }

    @Override
    public void beforeTextChanged(CharSequence text, int start, int count, int after) {
        if (doseQuantityList.contains(text.toString())) {
            doseQuantityList.remove(text.toString());
        }
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        doseQuantityList.add(editable.toString());
    }

    @Override
    public void closeActivity() {
        getActivity().finish();
    }
}
