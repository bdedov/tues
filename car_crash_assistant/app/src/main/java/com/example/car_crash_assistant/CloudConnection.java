package com.example.car_crash_assistant;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class CloudConnection
{
    public static final String MEASUREMENT_URL = "https://bdedov.1.stage.c8y.io/measurement/measurements";
    public static final String ALARM_URL = "https://bdedov.1.stage.c8y.io/alarm/alarms";
    public static final String LOCATION_URL = "https://bdedov.1.stage.c8y.io/inventory/managedObjects/4719658";
    private static String auth_token;
    private static String auth_websock_token;
    private static RequestQueue queue;

    public static boolean create(String username, String password, Context context)
    {
        generateToken(username, password);

        boolean isAuthorized = check_credentials();

        if(isAuthorized)
            queue = Volley.newRequestQueue(context);

        return isAuthorized;
    }

    private static void generateToken(String username, String password) {
        String str = username + ":" + password;
        String web_str = "t3193151/" + username + ":" + password;
        auth_token = android.util.Base64.encodeToString(str.getBytes(), android.util.Base64.NO_WRAP);
        auth_websock_token = android.util.Base64.encodeToString(web_str.getBytes(), android.util.Base64.NO_WRAP);
    }

    public static void post_measurement(JSONObject json)
    {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, MEASUREMENT_URL, json,
                new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                }
            }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Basic " + auth_token);
                    params.put("Content-Type", "application/json");
                    return params;
                }
            };

        queue.add(jsonRequest);
    }

    public static void post_location(JSONObject coords)
    {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, LOCATION_URL, coords,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Basic " + auth_token);
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        queue.add(jsonRequest);
    }


    public static void post_alarm(JSONObject json)
    {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, ALARM_URL, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Basic " + auth_token);
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        queue.add(jsonRequest);
    }

    private static boolean check_credentials() {
        int response_code = 0;

        try
        {
            URL url = new URL("https://bdedov.1.stage.c8y.io/platform");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Basic " + auth_token);
            connection.connect();

            response_code = connection.getResponseCode();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return response_code != 401;
    }

    public static String getWebSockToken()
    {
        return auth_websock_token;
    }
}
