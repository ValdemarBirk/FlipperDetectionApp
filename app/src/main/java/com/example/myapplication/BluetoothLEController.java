package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
            public void onClick(View v) {
                startScanWithCountdown();
            }
        });
    }

    private void startScanWithCountdown() {
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
                    numberOfScans++;
                    if (result.getDevice().getAddress().startsWith("80:E1")) {
                        leDeviceListAdapter.addDevice(result.getDevice());
                        leDeviceListAdapter.notifyDataSetChanged();
                        numberOfScans = 0;
                    }
                    if (numberOfScans > 30) {
                        leDeviceListAdapter.clear();
                        leDeviceListAdapter.notifyDataSetChanged();
                    }
                    adapter.notifyDataSetChanged();
                }
            };
}
