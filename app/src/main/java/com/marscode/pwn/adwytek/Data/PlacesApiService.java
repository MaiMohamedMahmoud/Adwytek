package com.marscode.pwn.adwytek.Data;

import com.marscode.pwn.adwytek.Model.PlacesNearByResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesApiService {

    /*
     * @Query() appends the parameter to the HTTP request.
     * In this case, the request made by retrofit looks like
     * BASEURL  https://maps.googleapis.com/maps/api/place/nearbysearch/json" +"?location=" + currentLat + "," + currentLong + "&radius=5000" +"&type=" + type +"&sensor=true" +"&key=AIzaSyDjBqWqYDF9BTX_gSpKIO4CydZwnp6hX-g";
     * */
    @GET("api/place/nearbysearch/json")
    Call<PlacesNearByResponse> getNearByPlaces(@Query("location") String location,
                                               @Query("radius") String radius,
                                               @Query("type") String type,
                                               @Query("sensor") String signature,
                                               @Query("key") String key);

}


