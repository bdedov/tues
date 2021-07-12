package com.example.car_crash_assistant;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void submit_button_on_click(View view) {
        String username, password;
        Snackbar error_message; // this is basically a messagebox

        // get the user input

        username = ((EditText) (findViewById(R.id.username))).getText().toString();
        password = ((EditText) (findViewById(R.id.password))).getText().toString();

        if (username.equals("") || password.equals(""))
        {
            // the user has left blank fields

            error_message = Snackbar.make(view, "Invalid input", BaseTransientBottomBar.LENGTH_SHORT);

            error_message.show();
        }
        else if (!CloudConnection.create(username, password, this))
        {
            // the user has entered invalid credentials

            error_message = Snackbar.make(view, "Invalid credentials", BaseTransientBottomBar.LENGTH_SHORT);

            error_message.show();
        }
        else
        {
            // the user has been validated

            Intent executing_activity_intent = new Intent(this, ExecutingActivity.class);

            this.startActivity(executing_activity_intent);

            new Event().start(); //start event listener
        }
    }
}