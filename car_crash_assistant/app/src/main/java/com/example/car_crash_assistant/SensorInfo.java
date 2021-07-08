package com.example.car_crash_assistant;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import org.json.JSONException;


public class SensorInfo implements SensorEventListener
{
    private Context context;
    private Sensor accelerometer;
    private SensorManager sensorManager;

    public SensorInfo(Context context)
    {
        this.context = context;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        CloudConnection.context = context;
    }


    @Override
    public void onSensorChanged(SensorEvent event)
    {
        try
        {
            Measurement.send(event.values);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public boolean check_for_issues()
    {

        return true; // TODO!
    }
}