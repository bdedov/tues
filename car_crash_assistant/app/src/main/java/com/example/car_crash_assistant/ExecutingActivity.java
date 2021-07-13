package com.example.car_crash_assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ExecutingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executing);
    }

    @Override
    protected void onStart() {
        // start the background processing of sensor data and listening for issues

        Intent executing_service_intent = new Intent(this.getBaseContext(), ExecutingService.class);

        super.onStart();

        this.startService(executing_service_intent);
    }
}