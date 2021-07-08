package com.example.car_crash_assistant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    AlertDialog errorMessage;
    SensorInfo sensor_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorMessage = new AlertDialog.Builder(this).create();
        errorMessage.setTitle("Login failed");
        errorMessage.setMessage("Invalid tenant_id or password!");
        errorMessage.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    public void get_input(View view)
    {
        EditText username = findViewById(R.id.username);
        String username_text = username.getText().toString();

        EditText password = findViewById(R.id.password);
        String password_text = password.getText().toString();

        CloudConnection.create(username_text, password_text, this);
        sensor_activity = new SensorInfo(this);

    }
}