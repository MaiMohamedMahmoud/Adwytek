package com.marscode.pwn.adwytek.Screens.MapPage;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.marscode.pwn.adwytek.Data.ApiUtils;
import com.marscode.pwn.adwytek.Model.Geometry;
import com.marscode.pwn.adwytek.Model.PlacesNearByResponse;
import com.marscode.pwn.adwytek.R;
import com.marscode.pwn.adwytek.Screens.FragmentBuilder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment implements MapPageInterface.MapPageView{

    //initialize variables
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0, currentLong = 0;
    GoogleMap map;
    private LatLng marker;
    MapPagePresenter mMapPagePresentation;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);

        //Assign variable
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mMapPagePresentation = new MapPagePresenter(new MapPageInteractor(),this);

        getCurrentLocation();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void getCurrentLocation() {
        Log.i("yarab", "call current loc");
        //check permission granted
        // if permission granted  getCurrentLocation Lat / lan
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i("yarab", "permission granted");
            Task<Location> locationTask = fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        //get current lat / lang and use them for show current location on map
                        currentLat = location.getLatitude();
                        currentLong = location.getLongitude();
                        // use current lat / long to create new object of latlong then use it in cameraUpdate object
                        Log.i("yarab ", "locationtask");

                        final LatLng latLng = new LatLng(currentLat, currentLong);
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {

                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                map = googleMap;
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                                addMarker(currentLat, currentLong, "Current Location");
                                getNearByLocation("pharmacy");
                            }
                        });
                    }
                }
            });
        } else {
            //permission Denied
            //request permission
            Log.i("yarab", "permission denied");
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

    public void addMarker(double Lat, Double Lng, String Title) {
        marker = new LatLng(Lat, Lng);
        map.addMarker(new MarkerOptions().position(marker).title(Title));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(marker, 10.0f));

    }

    public void getNearByLocation(String type) {
//        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
//                "?location=" + currentLat + "," + currentLong +
//                "&radius=5000" +
//                "&type=" + type +
//                "&sensor=true" +
//                "&key=AIzaSyDjBqWqYDF9BTX_gSpKIO4CydZwnp6hX-g";
        String location = currentLat + "," + currentLong;
        String radius = "5000";
        String sensor = "true";
        String key = getString(R.string.maps_api_key);
        mMapPagePresentation.fetchPharmacies(location, radius, type, sensor, key);
    }


    @Override
    public void showNearByLocOnMap(List<Geometry> geometryList) {
         for(int i =0 ;i<geometryList.size();i++){
             addMarker(geometryList.get(i).getLocation().getLat(),geometryList.get(i).getLocation().getLng(),"Name"+i);
         }
    }
}