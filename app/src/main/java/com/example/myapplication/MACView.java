package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MACView extends Activity {
    private TextView macAddressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macview);

        // Initialize the TextView
        macAddressTextView = findViewById(R.id.macAddressTextView);

        // Get the friendly name and MAC address from the intent
        String flipperName = getIntent().getStringExtra("flipper");
        String deviceAddress = getIntent().getStringExtra("deviceAddress");

        // Display the friendly name and MAC address in the TextView
        macAddressTextView.setText("Device Information:\nFriendly Name: " + flipperName + "\nMAC Address: " + deviceAddress);
    }
}

