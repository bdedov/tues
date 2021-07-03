package com.example.car_crash_assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submit_button_on_click(View view) {
        EditText username = findViewById(R.id.username);
        String username_text = username.getText().toString();

        EditText password = findViewById(R.id.password);
        String password_text = password.getText().toString();

        EditText result = findViewById(R.id.result); //print the result to the screen
        result.setText(username_text + "  " + password_text);
    }
}