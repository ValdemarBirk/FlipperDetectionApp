package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
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

