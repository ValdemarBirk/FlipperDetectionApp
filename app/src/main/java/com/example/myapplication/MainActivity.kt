package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.myapplication.API.AroundME.LocationPermissionManager;

class MainActivity : AppCompatActivity() {

    private lateinit var permissionManager: LocationPermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@MainActivity,BluetoothLEController::class.java ))
        // Check Bluetooth permission before starting BluetoothLEController
        permissionManager = LocationPermissionManager(this)
        permissionManager.checkLocationPermission()
        permissionManager.requestSingleLocationUpdate()
        checkBluetoothPermission()
    }

    private fun checkBluetoothPermission() {
        if (hasBluetoothPermission()) {
            startBluetoothOperations()
        } else {
            // Request Bluetooth permission
            requestPermissions(arrayOf(android.Manifest.permission.BLUETOOTH_SCAN), BLUETOOTH_PERMISSION_REQUEST_CODE)
        }
    }

    private fun hasBluetoothPermission(): Boolean {
        return checkSelfPermission(android.Manifest.permission.BLUETOOTH_SCAN) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == BLUETOOTH_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                startBluetoothOperations()
            } else {
                //handle not requested condition
            }
        }
    }

    private fun startBluetoothOperations() {

        GlobalScope.launch(Dispatchers.IO) {
            val locationData = Intent(this@MainActivity, BluetoothLEController::class.java)
            locationData.putExtra("lat", permissionManager.Globallatitude)
            locationData.putExtra("lon", permissionManager.Globallongitude)
        }
    }

    companion object {
        private const val BLUETOOTH_PERMISSION_REQUEST_CODE = 1001
    }


    /*        val bluetoothLEController : BluetoothLEController = BluetoothLEController();
                bluetoothLEController.scanLeDevice();

     */
}
