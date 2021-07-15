package com.example.car_crash_assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import org.json.JSONException;

public class PromptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);
    }

    public void affirmative_button_on_click(View view) {
        try
        {
            Alarm.send();
        }
        catch(JSONException exception)
        {
            this.finish();
        }

        this.finish();
    }

    public void negative_button_on_click(View view) {
        this.finish();
    }
}