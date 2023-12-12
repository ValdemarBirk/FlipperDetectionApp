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
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.API.AroundME.GetSurroundingData;
import com.example.myapplication.API.AroundME.NearbyFlipper;
import com.example.myapplication.API.DeviceDataCallback;
import com.example.myapplication.API.GetDeviceData;
import android.os.AsyncTask;

@SuppressLint("MissingPermission")

public class BluetoothLEController extends Activity implements DeviceDataCallback {

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

        Button myButton = findViewById(R.id.myButton);

        Button aroundMeButton = findViewById(R.id.aroundMeButton);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "button clicked", Toast.LENGTH_SHORT).show();
                new GetDeviceDataTask().callDeviceDataApi();

            }
        });

        aroundMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "button clicked", Toast.LENGTH_SHORT).show();
                new GetDevicesAroundMeTask().callApi();

            }
        });



        bluetoothManager = this.getSystemService(BluetoothManager.class);
        bluetoothAdapter = bluetoothManager.getAdapter();
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

        RecyclerView rw = findViewById(R.id.flippersFoundRecycler);
        rw.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewAdapterFlipper(this, leDeviceListAdapter);
        rw.setAdapter(adapter);

        scanLeDevice();
    }

    @Override
    public void onDeviceDataFetched(NearbyFlipper nearbyFlipper) {
        Log.d("callback is working", "onDeviceDataFetched: "+ nearbyFlipper);
        Intent intent = new Intent(BluetoothLEController.this, DisplayApiResultsActivity.class);
        intent.putExtra("fetchedData", nearbyFlipper);
        startActivity(intent);
    }

    private class GetDeviceDataTask {
        protected void callDeviceDataApi() {
            GetDeviceData getDeviceData = new GetDeviceData(BluetoothLEController.this);
            getDeviceData.FlipperData();
        }
    }

    private class GetDevicesAroundMeTask {
                protected void callApi() {
                GetSurroundingData getSurroundingData = new GetSurroundingData(getIntent().getDoubleExtra("lat", 0), getIntent().getDoubleExtra("lon", 0), BluetoothLEController.this);
                getSurroundingData.GetIntel();
            }
    }

    public void scanLeDevice() {
        bluetoothLeScanner.startScan(leScanCallback);
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
                        leDeviceListAdapter.addDevice(result.getDevice());
                        leDeviceListAdapter.notifyDataSetChanged();
                        numberOfScans = 0;
                    }
                    if(numberOfScans > 30)
                    {
                        leDeviceListAdapter.clear();
                        leDeviceListAdapter.notifyDataSetChanged();
                    }
                    adapter.notifyDataSetChanged();


                }
               };

}
