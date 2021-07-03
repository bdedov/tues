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
        TextInputLayout tenant_id_text_input_layout, username_text_input_layout, password_text_input_layout;
        Editable tenant_id, username, password;

        tenant_id_text_input_layout =  findViewById(R.id.id_input);

        username_text_input_layout = findViewById(R.id.username_input);

        password_text_input_layout = findViewById(R.id.password_input);

        if (tenant_id_text_input_layout == null || username_text_input_layout == null || password_text_input_layout == null)
        {
           Snackbar invalid_input_message = Snackbar.make(view, "Invalid input", BaseTransientBottomBar.LENGTH_SHORT);

           invalid_input_message.show();
        }
    }
}