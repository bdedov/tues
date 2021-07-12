package com.example.car_crash_assistant;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;

public class Event extends Thread
{
    private String msg;
    private final String URL = "wss://bdedov.1.stage.c8y.io/notification/realtime";

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
            final String clientId[] = {""};
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
                            /* Insert alarm logic here */
                            //Alarm.send();
                        }
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
