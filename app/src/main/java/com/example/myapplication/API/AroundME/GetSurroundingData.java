package com.example.myapplication.API.AroundME;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetSurroundingData {

    public void GetIntel() {
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl("https://api.wigle.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AroundMeService aroundMeservice = retrofitBuilder.create(AroundMeService.class);

        String auth = "Basic " + "insert your token";

        String netId = "80:e1:26";

        int lat = 40;

        int lon = -74;

        // Make the network request asynchronously
        Call<FlippersAroundMe> flippersAroundMeWigle = aroundMeservice.getDevicesAroundMe(auth, false,"40", "-74",netId,true,true
        );
        try {
            // Execute the request synchronously
            Response<FlippersAroundMe> response = flippersAroundMeWigle.execute();

            // Check if the request was successful
            if (response.isSuccessful()) {
                FlippersAroundMe responseBody = response.body();
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

