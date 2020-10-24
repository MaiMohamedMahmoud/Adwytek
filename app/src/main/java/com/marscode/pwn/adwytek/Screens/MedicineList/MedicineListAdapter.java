package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marscode.pwn.adwytek.Model.Dose;
import com.marscode.pwn.adwytek.Model.Medicine;
import com.marscode.pwn.adwytek.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.MedicineViewHolder> {
    List<Medicine> medicineList;

    MedicineListAdapter(List<Medicine> medicineList) {
        this.medicineList = medicineList;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_list_item, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }


    public class MedicineViewHolder extends RecyclerView.ViewHolder {
        TextView txt_medicine_name;
        TextView txt_medicine_Date;
        TextView txt_medicine_times;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_medicine_name = itemView.findViewById(R.id.txt_medicine_name);
            txt_medicine_Date = itemView.findViewById(R.id.medicine_startdate);
            txt_medicine_times = itemView.findViewById(R.id.medicine_times);
        }

        public void bind(int position) {
            txt_medicine_name.setText(medicineList.get(position).getName());
            //here list of days // when it is okay....

            DateFormat date = new SimpleDateFormat("dd MMM");
            String list_of_days = "";
            String dateStartedFormatted = date.format(medicineList.get(position).start_date);
            String dateEndFormatted = date.format(medicineList.get(position).end_date);
            final HashMap<String, Dose> hashMap = medicineList.get(position).doses;
            final List<Dose> listing = new ArrayList<>();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                hashMap.keySet().forEach(new Consumer<String>() {
                    @Override
                    public void accept(String key) {
                        listing.add(hashMap.get(key));
                    }
                });
            }

            for (int i = 0; i < listing.size(); i++) {
                if (i == listing.size() - 1) {
                    list_of_days = list_of_days + listing.get(i).day;
                } else {
                    list_of_days = list_of_days + listing.get(i).day + " ,";
                }
            }

            txt_medicine_times.setText(medicineList.get(position).getFrequency_of_intake() + "x per day | " + list_of_days);

            txt_medicine_Date.setText(dateStartedFormatted + " - " + dateEndFormatted);
        }

    }
}
