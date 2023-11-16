package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.API.WigleAPIService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        startActivity(Intent(this@MainActivity,BluetoothLEController::class.java ));
        runBlocking {
            launch {
            val retrofitBuilder = Retrofit.Builder().baseUrl("https://api.wigle.net/api")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val service = retrofitBuilder.create(WigleAPIService::class.java)
            val currentMac = "";
            val currentFlipper = service.getBLEDeviceHistory(currentMac, false, true, true)
            }
        }
        /*
                val bluetoothLEController : BluetoothLEController = BluetoothLEController();
                bluetoothLEController.scanLeDevice();


         */
    }




}