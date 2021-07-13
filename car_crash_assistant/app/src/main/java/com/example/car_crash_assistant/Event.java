package com.example.car_crash_assistant;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;

public class Event extends Thread
{
    private String msg;
    private final String URL = "wss://bdedov.1.stage.c8y.io/notification/realtime";
    Service context;

    public Event(Service context)
    {
        this.context = context;
    }

    private final String handshake = "[\n" +
            "   {\n" +
            "      \"ext\":{\n" +
            "         \"com.cumulocity.authn\":{\n" +
            "            \"token\":" + "\"" + CloudConnection.getWebSockToken() +"\"\n" +
            "         }\n" +
            "      },\n" +
            "      \"id\":\"1\",\n" +
            "      \"version\":\"1.0\",\n" +
            "      \"minimumVersion\":\"1.0\",\n" +
            "      \"channel\":\"/meta/handshake\",\n" +
            "      \"supportedConnectionTypes\":[\n" +
            "         \"websocket\",\n" +
            "         \"long-polling\"\n" +
            "      ],\n" +
            "      \"advice\":{\n" +
            "         \"timeout\":60000,\n" +
            "         \"interval\":0\n" +
            "      }\n" +
            "   }\n" +
            "]";

    @Override
    public void run()
    {
        try
        {
            String clientId[] = {""};
            URI host = new URI(URL);
            WebSocketClient socket = new WebSocketClient(host) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                }

                @Override
                public void onMessage(String message) {
                    try
                    {
                        JSONObject object = new JSONObject(message.substring(1, message.length() - 1));
                        if (object.has("clientId")) {
                            clientId[0] = object.getString("clientId");
                        }
                        if (!object.has("successful")) {
                            if(object.getJSONObject("data").getJSONObject("data").getString("type").equals("CrashEvent")) {
                                Intent prompt_notification_intent = new Intent(context, PromptActivity.class);
                                PendingIntent prompt_notification_pending_intent = PendingIntent.getActivity(context, 0, prompt_notification_intent, 0);
                                Notification prompt_notification = new NotificationCompat.Builder(context, ExecutingService.prompt_channel_id)
                                        .setContentTitle("Attention")
                                        .setContentText("Is there an issue?")
                                        .setSmallIcon(R.drawable.ic_android)
                                        .setContentIntent(prompt_notification_pending_intent)
                                        .build();

                                context.startForeground(2, prompt_notification);
                            }
                        }
                        System.out.println(message);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            };

            socket.connectBlocking();
            socket.send(handshake);
            Thread.sleep(1000); //wait for server response

            if(clientId[0].equals("")) {
                /* Display message on the screen */
                throw new Exception("Server failed to respond");
            }

            msg = "[\n" +
                    "   {\n" +
                    "      \"id\":\"4\",\n" +
                    "      \"channel\":\"/meta/subscribe\",\n" +
                    "      \"subscription\":\"/events/4719658\",\n" +
                    "      \"clientId\":\"" + clientId[0] +"\"\n" +
                    "   }\n" +
                    "]";
            socket.send(msg);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}