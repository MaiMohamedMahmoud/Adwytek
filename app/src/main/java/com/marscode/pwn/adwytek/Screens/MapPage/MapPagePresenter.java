package com.marscode.pwn.adwytek.Screens.MapPage;

import android.content.Context;
import android.widget.Toast;

import com.marscode.pwn.adwytek.Model.Pharmacy;
import com.marscode.pwn.adwytek.Model.PlacesNearByResponse;
import com.marscode.pwn.adwytek.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapPagePresenter implements MapPageInterface.MapPagePresentation {

    MapPageInteractor mapPageInteractor;
    MapPageInterface.MapPageView mMapPageView;
    Context mContext;

    MapPagePresenter(MapPageInteractor mapPageInteractor, MapPageInterface.MapPageView mapPageView, Context context) {
        this.mapPageInteractor = mapPageInteractor;
        mMapPageView = mapPageView;
        mContext = context;
    }

    Callback<PlacesNearByResponse> placesNearByResponseCallback = new Callback<PlacesNearByResponse>() {
        @Override
        public void onResponse(Call<PlacesNearByResponse> call, Response<PlacesNearByResponse> response) {
            if (response.isSuccessful()) {
                PlacesNearByResponse placeNearbyObject = response.body();
                if (placeNearbyObject.getStatus().equals("OK") && placeNearbyObject.getPharmacies().size() > 0) {
                    List<Pharmacy> pharmaciesList = placeNearbyObject.getPharmacies();
                    setPharmacies(pharmaciesList);
                } else {
                    Toast.makeText(mContext, mContext.getString(R.string.errorNearBy), Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onFailure(Call<PlacesNearByResponse> call, Throwable t) {
            Toast.makeText(mContext, mContext.getString(R.string.errorNearBy), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void fetchPharmacies(String location, String radius, String type, String sensor, String key) {
        mapPageInteractor.fetchPharmacies(location, radius, type, sensor, key, placesNearByResponseCallback);
    }

    @Override
    public void setPharmacies(List<Pharmacy> list) {
        mMapPageView.showNearByLocOnMap(list);
    }

}
