package com.example.myapplication.API.AroundME;

import android.util.Log;

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


    public GetSurroundingData(double lat, double lon) {
        this.lat = Double.toString(lat);
        this.lon = Double.toString(lon);
    }
    public void GetIntel() {

        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl("https://api.wigle.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AroundMeService aroundMeservice = retrofitBuilder.create(AroundMeService.class);

        String auth = "Basic " + "TOKEN";

        String netId = "80:e1:26";

        Log.d("LocationUpdateReceived", "Latitude: " + lat + ", Longitude: " + lon);

        // Make the network request asynchronously
        Call<FlippersAroundMe> flippersAroundMeWigle = aroundMeservice.getDevicesAroundMe(auth, false, "40", "-74",netId,true,true, "10"
        );
        flippersAroundMeWigle.enqueue(new Callback<FlippersAroundMe>() {
            @Override
            public void onResponse(Call<FlippersAroundMe> call, Response<FlippersAroundMe> response) {
                    // Check if the request was successful
                    if (response.isSuccessful()) {
                        FlippersAroundMe responseBody = response.body();
                        Log.d("API_CALL", "API call successful");

                        // Handle the response here, for example:
                        if (responseBody != null) {
                            try {
                                JSONObject response_json = new JSONObject(response.body().toString());
                                Object test = response_json.get("totalResults");
                                Log.i("JSON Test", (String) test);
                            } catch (JSONException e) {
                                Log.e("API_CALL", e.getMessage());
                            }
                            // Do something with the response
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
            public void onFailure(Call<FlippersAroundMe> call, Throwable t) {
                Log.e("API_CALL", "Exception: " + t.getMessage());
            }
        });
    }
}

