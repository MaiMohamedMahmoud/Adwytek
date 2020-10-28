package com.marscode.pwn.adwytek.Screens.MapPage;

import com.marscode.pwn.adwytek.Model.Geometry;
import com.marscode.pwn.adwytek.Model.PlacesNearByResponse;

import java.util.List;

import retrofit2.Callback;


public interface MapPageInterface {

    interface MapPagePresentation {
        void fetchPharmacies(String location, String radius, String type, String sensor, String key);

        void setPharmacies(List<Geometry> list);

    }

    interface MapPageView {
        void showNearByLocOnMap(List<Geometry> geometryList);
    }

    interface MapPageInteractor {
        void fetchPharmacies(String location, String radius, String type, String sensor, String key, Callback callback);

        void setPharmacies(List<Geometry> geometryList);
    }
}
