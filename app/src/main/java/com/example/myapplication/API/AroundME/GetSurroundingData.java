package com.example.myapplication.API.AroundME;

import android.util.Log;

import com.example.myapplication.API.DeviceDataCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GetSurroundingData {

    public String lat = "";

    public String lon = "";

    private DeviceDataCallback callback;

    public GetSurroundingData(double lat, double lon, DeviceDataCallback callback ) {
        this.lat = Double.toString(lat);
        this.lon = Double.toString(lon);
        this.callback = callback;
    }
    public void GetIntel() {

        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl("https://api.wigle.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AroundMeService aroundMeservice = retrofitBuilder.create(AroundMeService.class);

        String auth = "Basic " + "INSERT YOUR TOKEN";

        String netId = "80:e1:26";

        Log.d("LocationUpdateReceived", "Latitude: " + lat + ", Longitude: " + lon);

        // Make the network request asynchronously
        Call<NearbyFlipper> flippersAroundMeWigle = aroundMeservice.getDevicesAroundMe(auth, false, lat, lon,netId,true,true, "10"
        );
        flippersAroundMeWigle.enqueue(new Callback<NearbyFlipper>() {
            @Override
            public void onResponse(Call<NearbyFlipper> call, Response<NearbyFlipper> response) {
                    // Check if the request was successful
                    if (response.isSuccessful()) {
                        //FlippersAroundMe responseBody = response.body();
                        NearbyFlipper responseBody = response.body();
                        Log.d("API_CALL", "API call successful");
                        Log.d("API_TEST", String.valueOf(response.body()));
                        // Handle the response here, for example:
                        if (response.body() != null) {
                            callback.onDeviceDataFetched(responseBody);
                            Log.i("API_TEST", response.body().toString());
                            // Do something with the response
                        }
                    } else {
                        // Handle unsuccessful request (e.g., error response)
                        Log.e("API_CALL", "API call failed");

                        System.out.println("Request failed: " + response.code());
                    }
            }
            @Override
            public void onFailure(Call<NearbyFlipper> call, Throwable t) {
                Log.e("API_CALL", "Exception: " + t.getMessage());
            }
        });
    }
}

