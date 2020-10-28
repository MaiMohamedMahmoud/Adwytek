package com.marscode.pwn.adwytek.Data;


import android.util.Log;

public class ApiUtils {
    public static final String BASE_URL = "https://maps.googleapis.com/maps/";

    public static PlacesApiService getPlacesService() {
        Log.i("yarab","inside getPlacesService");

        return RetrofitClient.getClient(BASE_URL).create(PlacesApiService.class);
    }
}
