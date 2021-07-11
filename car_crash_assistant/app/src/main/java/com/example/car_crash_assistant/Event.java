package com.example.car_crash_assistant;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class Event extends Thread
{
    private String url = "wss://bdedov.1.stage.c8y.io/notification/realtime";
    private URI host;
    private WebSocketClient socket;

    @Override
    public void run() {
        try
        {
            host = new URI(url);
            socket = new WebSocketClient(host) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {

                }

                @Override
                public void onMessage(String message) {
                    System.out.println(message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {

                }

                @Override
                public void onError(Exception ex) {

                }
            };
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        while(true);
    }
}
