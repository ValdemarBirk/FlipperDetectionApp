package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.myapplication.API.AroundME.NearbyFlipper;
import com.example.myapplication.API.GetDeviceData;
import com.example.myapplication.API.WigleAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MACView extends Activity {
    public NearbyFlipper deviceData;

    public void getDeviceData() {
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl("https://api.wigle.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WigleAPIService service = retrofitBuilder.create(WigleAPIService.class);

        Call<NearbyFlipper> currentFlipper = service.getBLEDeviceHistory(getIntent().getStringExtra("auth"), getIntent().getStringExtra("Mac"), false, true, true);
        currentFlipper.enqueue(new Callback<NearbyFlipper>() {
            @Override
            public void onResponse(Call<NearbyFlipper> call, Response<NearbyFlipper> response) {
                deviceData = response.body();
            }

            @Override
            public void onFailure(Call<NearbyFlipper> call, Throwable t) {
                Log.d("hejsa",t.getMessage());
            }
        });
    }

    private TextView macAddressTextView;
    private  TextView QueryOutputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macview);

        QueryOutputTextView = findViewById(R.id.QueryOutput);


        // Initialize the TextView
        macAddressTextView = findViewById(R.id.macAddressTextView);

        // Get the friendly name and MAC address from the intent
        String flipperName = getIntent().getStringExtra("flipper");
        String deviceAddress = getIntent().getStringExtra("Mac");

        // Display the friendly name and MAC address in the TextView
        macAddressTextView.setText("Device Information:\nFriendly Name: " + flipperName + "\nMAC Address: " + deviceAddress);

        ImageButton dropdownButton = findViewById(R.id.dropdownMenuButton);
        dropdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDropdownMenu(view);
            }
        });
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button querryButton = findViewById(R.id.QueryToWigle);
        querryButton.setOnClickListener(new View.OnClickListener() {
            Retrofit retrofitBuilder = new Retrofit.Builder()
                    .baseUrl("https://api.wigle.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WigleAPIService service = retrofitBuilder.create(WigleAPIService.class);
            @Override
            public void onClick(View view) {


                Call<NearbyFlipper> currentFlipper = service.getBLEDeviceHistory(getIntent().getStringExtra("auth"), getIntent().getStringExtra("Mac"), false, true, true);
                currentFlipper.enqueue(new Callback<NearbyFlipper>() {
                    @Override
                    public void onResponse(Call<NearbyFlipper> call, Response<NearbyFlipper> response) {
                        deviceData = response.body();
                        if(response.body().getResultCount() == 0) {
                            QueryOutputTextView.setText("No result");
                            return;
                        }
                        QueryOutputTextView.setText(response.body().getResults().get(0).getCity());
                    }

                    @Override
                    public void onFailure(Call<NearbyFlipper> call, Throwable t) {
                        Log.d("hejsa",t.getMessage());
                    }
                });

            }
        });
    }
    public void showDropdownMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_dropdown, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_item_1){
                    Intent intent = new Intent(MACView.this, ContributorsActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }



}

