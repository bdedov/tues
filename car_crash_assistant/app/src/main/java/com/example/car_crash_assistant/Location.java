package com.example.car_crash_assistant;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

public class Location extends Thread {
    double[] coords;

    public Location()
    {
        coords = new double[2];
    }

    @Override
    public void run() {
        while(true)
        {
            try
            {
                coords[0] = 23.368717638514294;
                coords[1] = 42.64131671263959;

                LocationSender.send(coords);
                Thread.sleep(30000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
