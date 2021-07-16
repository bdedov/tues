package com.example.car_crash_assistant;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

public class Location implements LocationListener {
    LocationManager location_manager;

    double[] coords;

    public Location(Context context)
    {
        location_manager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);

        try
        {
            location_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, this);
        }
        catch(SecurityException exception)
        {
            exception.printStackTrace();
        }


        coords = new double[2];
    }

//    @Override
//    public void run()
//    {
//        try
//        {
//            fusedLocationProviderClient.getLastLocation();
//        }
//        catch (SecurityException e)
//        {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        try
        {
            coords[0] = location.getLongitude();
            coords[1] = location.getLatitude();

            LocationSender.send(coords);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
