package com.marscode.pwn.adwytek.Screens.MapPage;

import com.marscode.pwn.adwytek.Model.Geometry;
import com.marscode.pwn.adwytek.Model.Pharmacy;

import java.util.List;

import retrofit2.Callback;


public interface MapPageInterface {

    interface MapPagePresentation {
        void fetchPharmacies(String location, String radius, String type, String sensor, String key);

        void setPharmacies(List<Pharmacy> list);
    }

    interface MapPageView {
        void showNearByLocOnMap(List<Pharmacy> pharmacyList);
    }

    interface MapPageInteractor {
        void fetchPharmacies(String location, String radius, String type, String sensor, String key, Callback callback);

        void setPharmacies(List<Pharmacy> pharmacies);
    }
}
