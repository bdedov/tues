package com.example.car_crash_assistant;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Alarm
{
    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static void send() throws JSONException
    {
        JSONObject alarm = new JSONObject();
        JSONObject source = new JSONObject();
        
        source.put("name", "crash-app");
        source.put("id", "4719658");

        alarm.put("severity", "CRITICAL");
        alarm.put("time", formatter.format(new Date()));
        alarm.put("source", source);
        alarm.put("type", "c8y_Application");
        alarm.put("text", "Car accident");

        CloudConnection.post_alarm(alarm);
    }
}
