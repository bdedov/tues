package com.example.car_crash_assistant;

import org.json.*;

public class HTTPrequest
{
    public static String createRequest(String type, String url, String resource, String auth_token)
    {
        return type + " " + resource + " HTTP/1.1\r\n" +
                "Host: " + url + "\r\n" + "Authorization: Basic " + auth_token + "\r\n\r\n";
    }
}
