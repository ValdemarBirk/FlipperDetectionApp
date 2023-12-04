package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressLint("MissingPermission")

public class BluetoothLEController extends Activity {

    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;

    private BluetoothLeScanner bluetoothLeScanner;

    private RecyclerViewAdapterFlipper adapter;

    private LeDeviceListAdapter leDeviceListAdapter = new LeDeviceListAdapter();


    public BluetoothLEController() {

    }
    private boolean scanning;
    private Handler handler = new Handler();

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

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

        scanLeDevice();
    }


    public void scanLeDevice() {
        bluetoothLeScanner.startScan(leScanCallback);
    }

    public void stopLeDeviceScan(){
        bluetoothLeScanner.stopScan(leScanCallback);
    }

    private int numberOfScans = 0;

    // Device scan callback.
    private ScanCallback leScanCallback =
            new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                    numberOfScans++;
                    if(result.getDevice().getAddress().startsWith("80:E1")) {
                        if(result.getDevice().getUuids() != null){
                            for (int i = 0; i < result.getDevice().getUuids().length; i++) {
                                System.out.println("UUID: " + result.getDevice().getUuids()[i]);
                            }
                        }

                        leDeviceListAdapter.addDevice(result.getDevice());
                        leDeviceListAdapter.notifyDataSetChanged();
                        numberOfScans = 0;
                    }
                    if(numberOfScans > 500)
                    {
                        leDeviceListAdapter.clear();
                        leDeviceListAdapter.notifyDataSetChanged();
                    }
                    adapter.notifyDataSetChanged();


                }
               };

}
