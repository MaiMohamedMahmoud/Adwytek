//package com.marscode.pwn.adwytek.Screens.MapPage;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.coordinatorlayout.widget.CoordinatorLayout;
//import androidx.core.app.ActivityCompat;
//
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.location.Criteria;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.view.View;
//import android.widget.TextView;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.material.snackbar.Snackbar;
//import com.marscode.pwn.adwytek.R;
//
//public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
//        LocationListener {
//
//    private GoogleMap mMap;
//    LocationManager locationManager;
//    CoordinatorLayout mainCoordinatorLayout;
//    String type;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (!isGooglePlayServicesAvailable()) {
//            return;
//        }
//        setContentView(R.layout.activity_maps);
//
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        type="hospital";
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        mainCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.mainCoordinatorLayout);
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            showLocationSettings();
//        }
//    }
//
//    private void showLocationSettings() {
//        Snackbar snackbar = Snackbar
//                .make(mainCoordinatorLayout, "Location Error: GPS Disabled!",
//                        Snackbar.LENGTH_LONG)
//                .setAction("Enable", new View.OnClickListener() {
//                    @Override                    public void onClick(View v) {
//
//                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                    }
//                });
//        snackbar.setActionTextColor(Color.RED);
//        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
//
//        View sbView = snackbar.getView();
//        TextView textView = (TextView) sbView
//                .findViewById(android.support.design.R.id.snackbar_text);
//        textView.setTextColor(Color.YELLOW);
//
//        snackbar.show();
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setCompassEnabled(true);
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//
//        showCurrentLocation();
//    }
//
//    private void showCurrentLocation() {
//        Criteria criteria = new Criteria();
//        String bestProvider = locationManager.getBestProvider(criteria, true);
//        if (ActivityCompat.checkSelfPermission(this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        Location location = locationManager.getLastKnownLocation(bestProvider);
//
//        if (location != null) {
//            onLocationChanged(location);
//        }
//        locationManager.requestLocationUpdates(bestProvider, MIN_TIME_BW_UPDATES,
//                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//    }
//
//    private void loadNearByPlaces(double latitude, double longitude) {
//
//
//        StringBuilder googlePlacesUrl =
//                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
//        googlePlacesUrl.append("location=").append(latitude).append(",").append(longitude);
//        googlePlacesUrl.append("&radius=").append(PROXIMITY_RADIUS);
//        googlePlacesUrl.append("&types=").append(type);
//        googlePlacesUrl.append("&sensor=true");
//        googlePlacesUrl.append("&key=" + GOOGLE_BROWSER_API_KEY);
//
//        JsonObjectRequest request = new JsonObjectRequest(googlePlacesUrl.toString(),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject result) {
//
//                        Log.i(TAG, "onResponse: Result= " + result.toString());
//                        parseLocationResult(result);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override                    public void onErrorResponse(VolleyError error) {
//                        Log.e(TAG, "onErrorResponse: Error= " + error);
//                        Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
//                    }
//                });
//
//        AppController.getInstance().addToRequestQueue(request);
//    }
//
//    private void parseLocationResult(JSONObject result) {
//
//        String id, place_id, placeName = null, reference, icon, vicinity = null;
//        double latitude, longitude;
//
//        try {
//            JSONArray jsonArray = result.getJSONArray("results");
//
//            if (result.getString(STATUS).equalsIgnoreCase(OK)) {
//
//                mMap.clear();
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject place = jsonArray.getJSONObject(i);
//
//                    id = place.getString(SUPERMARKET_ID);
//                    place_id = place.getString(PLACE_ID);
//                    if (!place.isNull(NAME)) {
//                        placeName = place.getString(NAME);
//                    }
//                    if (!place.isNull(VICINITY)) {
//                        vicinity = place.getString(VICINITY);
//                    }
//                    latitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
//                            .getDouble(LATITUDE);
//                    longitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
//                            .getDouble(LONGITUDE);
//                    reference = place.getString(REFERENCE);
//                    icon = place.getString(ICON);
//
//                    MarkerOptions markerOptions = new MarkerOptions();
//                    LatLng latLng = new LatLng(latitude, longitude);
//                    markerOptions.position(latLng);
//                    markerOptions.title(placeName + " : " + vicinity);
//
//                    mMap.addMarker(markerOptions);
//                }
//
//                Toast.makeText(getBaseContext(), jsonArray.length() + " Supermarkets found!",
//                        Toast.LENGTH_LONG).show();
//            } else if (result.getString(STATUS).equalsIgnoreCase(ZERO_RESULTS)) {
//                Toast.makeText(getBaseContext(), "No Supermarket found in 5KM radius!!!",
//                        Toast.LENGTH_LONG).show();
//            }
//
//        } catch (JSONException e) {
//
//            e.printStackTrace();
//            Log.e(TAG, "parseLocationResult: Error=" + e.getMessage());
//        }
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        double latitude = location.getLatitude();
//        double longitude = location.getLongitude();
//
//        LatLng latLng = new LatLng(latitude, longitude);
//        mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
//
//        loadNearByPlaces(latitude, longitude);
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//    }
//
//    @Override
//    public void onStatusChanged(String s, int i, Bundle bundle) {
//    }
//
//    @Override
//    public void onProviderEnabled(String s) {
//    }
//
//    @Override
//    public void onProviderDisabled(String s) {
//    }
//
//    private boolean isGooglePlayServicesAvailable() {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (apiAvailability.isUserResolvableError(resultCode)) {
//                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
//            } else {
//                Log.i(TAG, "This device is not supported.");
//                finish();
//            }
//            return false;
//        }
//        return true;
//    }
//}