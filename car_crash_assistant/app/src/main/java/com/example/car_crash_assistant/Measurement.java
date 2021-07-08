package com.example.car_crash_assistant;

import org.json.*;

public class Measurement
{
    public static void send(float[] acceleration) throws JSONException //will accept coordinates l8r
    {
        //DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        JSONObject measurement = new JSONObject();
        JSONObject source = new JSONObject();
        JSONObject c8y_Acceleration = new JSONObject();
        JSONObject c8y_Location = new JSONObject();
        JSONObject a_x = new JSONObject();
        JSONObject a_y = new JSONObject();
        JSONObject a_z = new JSONObject();
        JSONObject longitude = new JSONObject();
        JSONObject latitude = new JSONObject();

        source.put("id", "3362");
        a_x.put("value", acceleration[0]);
        a_x.put("unit", "g");
        a_y.put("value", acceleration[1]);
        a_y.put("unit", "g");
        a_z.put("value", acceleration[2]);
        a_z.put("unit", "g");

        c8y_Acceleration.put("x", a_x);
        c8y_Acceleration.put("y", a_y);
        c8y_Acceleration.put("z", a_z);

        measurement.put("time", "2011-09-19T12:03:27.845Z");
        measurement.put("type", "c8y_Acceleration");
        measurement.put("source", source);
        measurement.put("c8y_Acceleration", c8y_Acceleration);

        //CloudConnection.connect();
    }
}
