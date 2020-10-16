package com.example.attendanceapp.viewmodel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

public class LocationModel extends ViewModel {
    private LocationManager locationManager;
    private LocationListener listener;
    Context c;

   public MutableLiveData<Location>     locationMutableLiveData =new MutableLiveData<>();


    public void GetLocation(Context context) {
        c = context;
        locationManager = (LocationManager) c.getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
              locationMutableLiveData.setValue(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                c.startActivity(i);
            }
        };

        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, listener);


    }
}
