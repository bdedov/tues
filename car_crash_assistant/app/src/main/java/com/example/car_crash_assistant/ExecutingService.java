package com.example.car_crash_assistant;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class ExecutingService extends Service {
    public static String executing_channel_id, executing_channel_name;
    public static String prompt_channel_id, prompt_channel_name;

    public ExecutingService() {
        executing_channel_id = "0";

        executing_channel_name = "Service Punning Channel";

        prompt_channel_id = "1";

        prompt_channel_name = "User Prompt Channel";
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        SensorInfo sensor_info = new SensorInfo(this);
        Event event = new Event(this);

        event.start();

        super.onCreate();

        create_notification_channel(executing_channel_id, executing_channel_name, NotificationManager.IMPORTANCE_DEFAULT);

        create_notification_channel(prompt_channel_id, prompt_channel_name, NotificationManager.IMPORTANCE_HIGH);
    }

    private void create_notification_channel(String channel_id, String channel_name, int importance)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel service_channel = new NotificationChannel(channel_id, channel_name, importance);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(service_channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent executing_notification_intent = new Intent(this.getBaseContext(), ExecutingActivity.class);
        PendingIntent executing_notification_pending_intent = PendingIntent.getActivity(this, 0, executing_notification_intent, 0);
        Notification executing_notification = new NotificationCompat.Builder(this, executing_channel_id)
                .setContentTitle("Successfully started")
                .setContentText("The sensor's data is being processed")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(executing_notification_pending_intent)
                .build();

        startForeground(1, executing_notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        this.stopSelf();
    }
}