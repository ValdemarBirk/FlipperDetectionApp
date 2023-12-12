package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.API.AroundME.NearbyFlipper;
import com.example.myapplication.API.AroundME.WiFiNetwork;

import java.util.List;

public class DisplayApiResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aroundme_layout);

        // Retrieve the passed data from the Intent
        NearbyFlipper fetchedData = getIntent().getParcelableExtra("fetchedData");
        List<WiFiNetwork> results = fetchedData.getResults();
        Log.d("fetched Data", "onCreate: " + fetchedData);

        LinearLayout linearLayout = findViewById(R.id.parentLayout);

        // Render the fetched data in your UI elements
        if (fetchedData != null) {
            for (WiFiNetwork result : results) {
                // Create a new TextView for each WiFiNetwork field and add it to the layout
                addTextView(linearLayout, "netid: " + result.getNetid());
                addTextView(linearLayout, "SSID: " + result.getSsid());
                addTextView(linearLayout, "Device: " + result.getDevice());
                addTextView(linearLayout, "Type: " + result.getType());
                addTextView(linearLayout, "City: " + result.getCity());
                addTextView(linearLayout, "Country: " + result.getCountry());
                addTextView(linearLayout, "firstTime: " + result.getFirsttime());
                addTextView(linearLayout, "lastTime: " + result.getLasttime());

            }
        }
    }

    private void addTextView(LinearLayout parentLayout, String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        parentLayout.addView(textView);
    }
}
