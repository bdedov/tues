package com.example.car_crash_assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ExecutingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SensorInfo sensor_info; // handles the information from the sensors

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executing);

        sensor_info = new SensorInfo(this);
    }
}