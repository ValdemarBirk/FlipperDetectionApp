package com.example.myapplication.API.AroundME;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface AroundMeService {
    @GET("api/v2/bluetooth/search?")
    Call<NearbyFlipper> getDevicesAroundMe(
            @Header("Authorization") String authorizationHeader,
            @Query("onlymine") boolean onlymine,
            @Query("closestLat") String latitude,
            @Query("closestLong") String longitude,
            @Query("netid") String mac_addr,
            @Query("showBt") boolean showBt,
            @Query("showBle") boolean showBle,
            @Query("resultsPerPage") String resultsPerPage
    );
}
