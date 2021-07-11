package com.example.car_crash_assistant;

import org.json.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Measurement
{
    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private final static JSONObject measurement = new JSONObject();
    private final static JSONObject source = new JSONObject();
    private final static JSONObject c8y_Acceleration = new JSONObject();
    private final static JSONObject a_x = new JSONObject();
    private final static JSONObject a_y = new JSONObject();
    private final static JSONObject a_z = new JSONObject();


    public static void send(float[] acceleration) throws JSONException //will accept coordinates l8r
    {
        source.put("id", "4719658");
        a_x.put("value", acceleration[0]);
        a_x.put("unit", "G");
        a_y.put("value", acceleration[1]);
        a_y.put("unit", "G");
        a_z.put("value", acceleration[2]);
        a_z.put("unit", "G");

        c8y_Acceleration.put("accelerationY", a_y);
        c8y_Acceleration.put("accelerationX", a_x);
        c8y_Acceleration.put("accelerationZ", a_z);

        measurement.put("time", formatter.format(new Date()));
        measurement.put("source", source);
        measurement.put("type", "c8y_Acceleration");
        measurement.put("c8y_Acceleration", c8y_Acceleration);

        CloudConnection.post_measurement(measurement);
    }
}
