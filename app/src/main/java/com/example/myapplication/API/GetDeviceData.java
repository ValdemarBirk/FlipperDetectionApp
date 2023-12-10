package com.example.myapplication.API;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;

public class GetDeviceData {

    public void FlipperData() {
        Retrofit retrofitBuilder = new Retrofit.Builder()
            .baseUrl("https://api.wigle.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        WigleAPIService service = retrofitBuilder.create(WigleAPIService.class);

        String currentMac = "80:e1:26";

        String authHeader = "Basic " + "insert token";

        // Make the network request asynchronously
        Call<BLEDevice> currentFlipper = service.getBLEDeviceHistory(authHeader, currentMac, false, true, true);

        try {
            // Execute the request synchronously
            Response<BLEDevice> response = currentFlipper.execute();

            // Check if the request was successful
            if (response.isSuccessful()) {
                BLEDevice responseBody = response.body();
                Log.d("API_CALL", "API call successful");

                // Handle the response here, for example:
                if (responseBody != null) {
                    // Do something with the response
                    System.out.println(responseBody);
                    System.out.println("hi"); // or any other operation you want to perform
                }
            } else {
                // Handle unsuccessful request (e.g., error response)
                Log.e("API_CALL", "API call failed");

                System.out.println("Request failed: " + response.code());
            }
        } catch (IOException e) {
            // Handle exceptions if any occur during the request
            Log.e("API_CALL", "Exception: " + e.getMessage());
        }
    }
}
