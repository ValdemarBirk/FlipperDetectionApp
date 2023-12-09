package com.example.myapplication.API.AroundME;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface AroundMeService {
    @GET("api/v2/bluetooth/search?")
    Call<FlippersAroundMe> getDevicesAroundMe(
            @Query("onlymine") boolean onlymine,
            @Query("closestLat") String latitude,
            @Query("closestLong") String longitude,
            @Query("netid") String mac_addr,
            @Query("showBt") boolean showBt,
            @Query("showBle") boolean showBle
    );
}
