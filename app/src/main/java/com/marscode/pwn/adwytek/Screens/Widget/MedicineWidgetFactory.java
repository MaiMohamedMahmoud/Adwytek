package com.marscode.pwn.adwytek.Screens.Widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.marscode.pwn.adwytek.Model.Medicine;
import com.marscode.pwn.adwytek.R;

import java.lang.reflect.Type;
import java.util.List;

public class MedicineWidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    List<Medicine> medicineList;

    MedicineWidgetFactory(List<Medicine> medicineList, Context context) {
        mContext = context;
        this.medicineList = medicineList;

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences sharedPref = mContext.getSharedPreferences(mContext.getString(R.string.MedicineListPref), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString(mContext.getString(R.string.MedicineListObj), "");
        Type type = new TypeToken<List<Medicine>>() {
        }.getType();
        List<Medicine> recipeObj = gson.fromJson(json, type);

        medicineList = recipeObj;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        //here return the Recipes Count
        return medicineList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        //here build your item
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.medicine_app_widget_item_list);
        remoteViews.setImageViewResource(R.id.image_medicine_widget, R.drawable.medicineicon);
        remoteViews.setTextViewText(R.id.medicine_txt_widget, medicineList.get(i).getName());
        //remoteViews.setTextViewText(R.id.serving_number_txt_widget, " " + medicineList.get(i).getFrequency_of_intake() );

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
