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
@SuppressLint("MissingPermission")

public class BluetoothLEController extends Activity {

    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;

    private BluetoothLeScanner bluetoothLeScanner;


    public BluetoothLEController() {
    }
    private boolean scanning;
    private Handler handler = new Handler();

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bluetoothManager = this.getSystemService(BluetoothManager.class);
        bluetoothAdapter = bluetoothManager.getAdapter();
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        scanLeDevice();
    }


    public void scanLeDevice() {
        if (!scanning) {
            // Stops scanning after a predefined scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanning = false;
                    bluetoothLeScanner.stopScan(leScanCallback);
                }
            }, SCAN_PERIOD);
            scanning = true;
            bluetoothLeScanner.startScan(leScanCallback);
        } else {
            scanning = false;
            bluetoothLeScanner.stopScan(leScanCallback);
        }
    }


    private LeDeviceListAdapter leDeviceListAdapter = new LeDeviceListAdapter();

    // Device scan callback.
    private ScanCallback leScanCallback =
            new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                    if(result.getDevice().getAddress().startsWith("80:E1")){
                        leDeviceListAdapter.addDevice(result.getDevice());
                        leDeviceListAdapter.notifyDataSetChanged();
                    }
                    System.out.println(leDeviceListAdapter.getCount());
                    for (int i = 0; i < leDeviceListAdapter.getCount(); i++) {
                        System.out.println(leDeviceListAdapter.getDevice(i).getAddress());
                    }

                }
            };
}
