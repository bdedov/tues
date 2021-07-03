package com.example.car_crash_assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    CloudConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void get_input(View view) {
        EditText tenant_id = findViewById(R.id.tenant_id);
        String tenant_text = tenant_id.getText().toString();

        EditText password = findViewById(R.id.password);
        String password_text = password.getText().toString();

        if(!CloudConnection.connect(tenant_text, password_text))
        {
            //login error: prompt again
        }
    }
}