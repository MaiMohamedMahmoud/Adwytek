package com.marscode.pwn.adwytek.Screens.MedicineList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marscode.pwn.adwytek.Model.Medicine;
import com.marscode.pwn.adwytek.R;

import java.util.List;

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

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_medicine_name = itemView.findViewById(R.id.txt_medicine_name);

        }

        public void bind(int position) {
            txt_medicine_name.setText(medicineList.get(position).getMedicineName());
        }

    }
}
