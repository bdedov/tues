package com.example.car_crash_assistant;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationSender
{
    public static void send(double[] coordinates) throws JSONException
    {
        System.out.println(coordinates[0] + " " + coordinates[1]);

        JSONObject object = new JSONObject();
        JSONObject location = new JSONObject();

        location.put("lng", coordinates[0]);
        location.put("lat", coordinates[1]);

        object.put("c8y_Position", location);

        CloudConnection.post_location(object);
    }
}