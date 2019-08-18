package com.example.tom.mineclicker;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationClass extends Application {
    public static final String  CHANNEL_1_iD = "RockDefeated";


    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();

    }
    private void createNotificationChannels(){
     if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
         NotificationChannel channel1 = new NotificationChannel(
                 CHANNEL_1_iD,
                 "Rock Defetead",
                 NotificationManager.IMPORTANCE_HIGH
         );
         channel1.setDescription("this is a message for defeating a rock");

         NotificationManager manager = getSystemService(NotificationManager.class);
         manager.createNotificationChannel(channel1);

     }
    }
}
