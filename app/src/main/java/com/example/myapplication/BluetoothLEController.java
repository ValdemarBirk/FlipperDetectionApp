package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.API.AroundME.GetSurroundingData;

@SuppressLint("MissingPermission")

public class BluetoothLEController extends Activity {

    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private RecyclerViewAdapterFlipper adapter;
    private LeDeviceListAdapter leDeviceListAdapter = new LeDeviceListAdapter();
    private boolean scanning;
    private Handler handler = new Handler();
    private static final long SCAN_PERIOD = 10000;
    private int numberOfScans = 0;

    // Add a CountdownTimer
    private CountDownTimer countDownTimer;
    private Button scanButton;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetoothManager = this.getSystemService(BluetoothManager.class);
        bluetoothAdapter = bluetoothManager.getAdapter();
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

        RecyclerView rw = findViewById(R.id.flippersFoundRecycler);
        rw.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewAdapterFlipper(this, leDeviceListAdapter);
        rw.setAdapter(adapter);

        // Initialize the button and set its click listener
        scanButton = findViewById(R.id.startScanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScanWithCountdown();
            }
        });
        ImageButton dropdownMenuButton = findViewById(R.id.dropdownMenuButton);
        dropdownMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDropdownMenu(view);
            }
        });
    }

    private void startScanWithCountdown() {
        // disabling the scan button to prevent multiple scans
        scanButton.setEnabled(false);
        // Start the countdown timer
        countDownTimer = new CountDownTimer(SCAN_PERIOD, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                // Update the button text with the countdown
                scanButton.setText("Scanning: " + millisUntilFinished / 1000 + "s");
            }

            @SuppressLint("SetTextI18n")
            public void onFinish() {
                // Perform actions after the countdown finishes
                scanButton.setText("Start Scan");
                // Stop scanning
                stopScan();
                //re-enable scan button
                scanButton.setEnabled(true);
            }
        }.start();

        // Start Bluetooth LE scan
        scanLeDevice();
    }

    public void scanLeDevice() {
        bluetoothLeScanner.startScan(leScanCallback);
    }

    private void stopScan() {
        bluetoothLeScanner.stopScan(leScanCallback);
        countDownTimer.cancel();
    }

    // Device scan callback.
    private ScanCallback leScanCallback =
            new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                    BluetoothDevice device = result.getDevice();
                    String deviceAddress = device.getAddress();
                    String deviceName = device.getName();

                    if (result.getDevice().getAddress().startsWith("80:E1")) {
                        leDeviceListAdapter.addDevice(result.getDevice());
                        adapter.notifyDataSetChanged();
                    }
                }
            };

    private class GetDevicesAroundMeTask {
        protected void callApi() {
            GetSurroundingData getSurroundingData = new GetSurroundingData(getIntent().getDoubleExtra("lat", 0), getIntent().getDoubleExtra("lon", 0));
            getSurroundingData.GetIntel();
        }
    }

    public void showDropdownMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_dropdown, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_item_1){
                    Intent intent = new Intent(BluetoothLEController.this, ContributorsActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
