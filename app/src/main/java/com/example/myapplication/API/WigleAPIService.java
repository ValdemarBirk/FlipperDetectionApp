package com.example.myapplication.API;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Header;

public interface WigleAPIService {

    @GET("api/v2/bluetooth/search?")
    Call<BLEDevice> getBLEDeviceHistory(
            @Header("Authorization") String authorizationHeader,
            @Query("netid") String mac_addr,
            @Query("onlymine") boolean onlymine,
            @Query("showBt") boolean showBt,
            @Query("showBle") boolean showBle
    );
}
