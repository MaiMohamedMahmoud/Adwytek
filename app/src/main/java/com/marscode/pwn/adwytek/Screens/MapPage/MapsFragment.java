package com.marscode.pwn.adwytek.Screens.MapPage;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.Maps;
import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.FragmentBuilder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class MapsFragment extends Fragment {

    //initialize variables
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0, currentLong = 0;
    GoogleMap map;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container,false);


        //Assign variable
        supportMapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());


        getCurrentLocation();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void getCurrentLocation() {
        //check permission granted
        // if permission granted  getCurrentLocation Lat / lan
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Task<Location> locationTask = fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        //get current lat / lang and use them for show current location on map
                        currentLat = location.getLatitude();
                        currentLong = location.getLongitude();
                        // use current lat / long to create new object of latlong then use it in cameraUpdate object
                        final LatLng latLng = new LatLng(currentLat, currentLong);
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {

                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                map = googleMap;
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            }
                        });
                    }
                }
            });
        } else {
            //permission Denied
            //request permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // if the grantResult is permisson granted
                //call getCurrentLocation()
                getCurrentLocation();
            }
        }
    }
}