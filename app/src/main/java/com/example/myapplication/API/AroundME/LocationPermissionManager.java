package com.example.myapplication.API.AroundME;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;

public class LocationPermissionManager {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private AppCompatActivity activity;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public String Globallatitude;

    public String Globallongitude;

    public LocationPermissionManager(AppCompatActivity activity) {
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Globallatitude = String.valueOf(latitude);
                    Globallongitude = String.valueOf(longitude);
                    // Perform operations with latitude and longitude
                    stopLocationUpdates();
                }
            }

            // Other overridden methods of LocationListener as needed
        };
    }

    public void requestSingleLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        Log.d("LocationUpdate", "Latitude: " + latitude + ", Longitude: " + longitude);
                        // Perform operations with latitude and longitude
                        stopLocationUpdates();
                    }
                }

                // Other overridden methods of LocationListener as needed
            }, null);
        }
    }

    public void checkLocationPermission() {
        if (hasLocationPermission()) {
            requestSingleLocationUpdate();
        } else {
            requestLocationPermission();
        }
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE
        );
    }

    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationUpdates();
            } else {
                // Location permission denied, handle accordingly
            }
        }
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    public void stopLocationUpdates() {
        locationManager.removeUpdates(locationListener);
    }
}
