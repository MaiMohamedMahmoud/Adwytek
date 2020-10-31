package com.marscode.pwn.adwytek.Screens.Widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViewsService;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.marscode.pwn.adwytek.Model.Medicine;
import com.marscode.pwn.adwytek.R;

import java.lang.reflect.Type;
import java.util.List;

public class MedicineWidgetService extends RemoteViewsService {


    public static List<Medicine> getSharedPrefRecipes(Context context) {

        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.MedicineListPref), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString(context.getString(R.string.MedicineListObj), "");
        Type type = new TypeToken<List<Medicine>>() {
        }.getType();
        List<Medicine> recipeObj = gson.fromJson(json, type);

        return recipeObj;
    }

    public static void updateWidget(Context context) {

//        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.recipeItemPref), Context.MODE_PRIVATE);
//        SharedPreferences.Editor prefsEditor = sharedPref.edit();
//        Gson gson = new Gson();
//
//        List<Medicine> recipeObj = getSharedPrefRecipes(context);
//        Recipe recipe = recipeObj.get(id);
//        String recipe_json = gson.toJson(recipe);
//        prefsEditor.putString(context.getString(R.string.recipeObjWidget), recipe_json);
//        prefsEditor.commit();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, MedicineAppWidget.class));
        MedicineAppWidget.updateAppWidgets(context, appWidgetManager, appWidgetIds);


    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        //List<Ingredient> IngredientList = getSharedPrefRecipes(getApplicationContext()).get(0).getIngredients();
        List<Medicine> medicineList = getSharedPrefRecipes(getApplicationContext());
        return new MedicineWidgetFactory(medicineList, getApplicationContext());
    }
}
