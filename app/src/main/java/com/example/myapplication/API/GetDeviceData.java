package com.example.myapplication.API;

import android.util.Log;

import com.example.myapplication.API.AroundME.NearbyFlipper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetDeviceData {

    private DeviceDataCallback callback;
    public GetDeviceData(DeviceDataCallback callback) {
        this.callback = callback;
    }

    public void FlipperData() {
        Retrofit retrofitBuilder = new Retrofit.Builder()
            .baseUrl("https://api.wigle.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        WigleAPIService service = retrofitBuilder.create(WigleAPIService.class);

        String currentMac = "MAC address";

        String authHeader = "Basic " + "TOKEN";

        // Make the network request asynchronously
        Call<NearbyFlipper> currentFlipper = service.getBLEDeviceHistory(authHeader, currentMac, false, true, true);

        currentFlipper.enqueue(new Callback<NearbyFlipper>() {
            @Override
            public void onResponse(Call<NearbyFlipper> call, Response<NearbyFlipper> response) {
                if (response.isSuccessful()) {
                    NearbyFlipper responseBody = response.body();
                    Log.d("API_CALL", "API call successful"+ responseBody);

                    // Handle the response here, for example:
                    if (responseBody != null) {
                        // Do something with the response
                        callback.onDeviceDataFetched(responseBody);
                        System.out.println(responseBody);
                        System.out.println("hi"); // or any other operation you want to perform
                    }
                } else {
                    // Handle unsuccessful request (e.g., error response)
                    Log.e("API_CALL", "API call failed");

                    System.out.println("Request failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<NearbyFlipper> call, Throwable e) {
                // Handle exceptions if any occur during the request
                Log.e("API_CALL", "Exception: " + e.getMessage());
            }
        });

    }
}
