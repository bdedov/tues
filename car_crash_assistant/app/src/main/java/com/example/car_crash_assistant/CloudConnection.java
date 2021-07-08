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
import java.util.HashMap;
import java.util.Map;

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

    public static String getToken() {
        return auth_token;
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

    private static boolean check_credentials()
    {
        int[] response_code = new int[1];

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://bdedov.1.stage.c8y.io/platform",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                response_code[0] = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Basic " + auth_token);
                return params;
            }
        };

        return response_code[0] != 401;
    }

//    public static void set_alarm(String severity)
//    {
//        // TODO!
//    }
}