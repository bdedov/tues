package com.example.car_crash_assistant;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class CloudConnection
{
    public static final String URL = "https://bdedov.1.stage.c8y.io/measurement/measurements";
    private static String auth_token;
    private static RequestQueue queue;

    public static boolean create(String username, String password, Context context)
    {
        generateToken(username, password);
        queue = Volley.newRequestQueue(context);

        return check_credentials();
    }

    private static void generateToken(String username, String password) {
        String str = username + ":" + password;
        auth_token = android.util.Base64.encodeToString(str.getBytes(), 0);
    }

    public static void post_measurement(JSONObject json)
    {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, URL, json,
                new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("Response: " + response.toString());
                }
            }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error: " + error.toString());
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
            java.net.URL url = new URL("https://bdedov.1.stage.c8y.io/platform");
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

//    public static void set_alarm(String severity)
//    {
//        // TODO!
//    }
}
