package com.marscode.pwn.adwytek.Screens.MapPage;

import android.util.Log;

import com.marscode.pwn.adwytek.Data.ApiUtils;
import com.marscode.pwn.adwytek.Model.Pharmacy;
import com.marscode.pwn.adwytek.Model.PlacesNearByResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class MapPageInteractor implements MapPageInterface.MapPageInteractor {


    @Override
    public void fetchPharmacies(String location, String radius, String type, String sensor, String key, Callback callback) {
        Log.i("yarab fetch interactor", location);


        Call<PlacesNearByResponse> placesCall = ApiUtils.getPlacesService().getNearByPlaces(location, radius, type, sensor, key);

        placesCall.enqueue(callback);
    }

    @Override
    public void setPharmacies(List<Pharmacy> pharmacies) {

    }
}
