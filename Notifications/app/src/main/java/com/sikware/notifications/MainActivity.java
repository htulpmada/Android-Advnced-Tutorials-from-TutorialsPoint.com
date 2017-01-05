package com.sikware.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private NotificationManager mNotificationManager;
    private int notificationID = 100;
    private int numMessages = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startBtn = (Button) findViewById(R.id.start);
        startBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                displayNotification();
            }
        });

        Button cancelBtn = (Button) findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                cancelNotification();
            }
        });

        Button updateBtn = (Button) findViewById(R.id.update);
        updateBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                updateNotification();
            }
        });

        Button bigBtn = (Button) findViewById(R.id.big);
        bigBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                displayBigNotification();
            }
        });
    }

    protected void displayNotification(){
        Log.i("Start","notification");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("New Message");
        mBuilder.setContentText("You've recieved a new message");
        mBuilder.setTicker("New Message Alert!!");
        mBuilder.setSmallIcon(R.drawable.stick);
        mBuilder.setNumber(++numMessages);

        Intent resultIntent = new Intent(this, NotificationView.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationView.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationID,mBuilder.build());
    }

    protected void displayBigNotification(){
        Log.i("Start","notification");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("New Message");
        mBuilder.setContentText("You've recieved a BIG message");
        mBuilder.setTicker("New Big Message Alert!!");
        mBuilder.setSmallIcon(R.drawable.stick);
        mBuilder.setNumber(++numMessages);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = {
                new String("This is the First Line...."),
                new String("Second Line..."),
                new String("Third..."),
                new String("4th line.."),
                new String("5th |.. "),
                new String("6th..")
        };
        
        inboxStyle.setBigContentTitle("BIG CONTENT TITLE:");
        for(String s: events){inboxStyle.addLine(s);}
        mBuilder.setStyle(inboxStyle);

        Intent resultIntent = new Intent(this, NotificationView.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationView.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationID,mBuilder.build());
    }

    protected void cancelNotification(){
        Log.i("Cancel", "notification");
        try{
            mNotificationManager.cancel(notificationID);
        }
        catch(NullPointerException n){n.printStackTrace();}
    }

    protected void updateNotification(){
        Log.i("Update","notification");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("Update Message");
        mBuilder.setContentText("You've got an Updated Message");
        mBuilder.setTicker("Updated Message Alert!!");
        mBuilder.setSmallIcon(R.drawable.stick);
        mBuilder.setNumber(++numMessages);
        Intent resultIntent = new Intent(this, NotificationView.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationView.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationID,mBuilder.build());
    }

}
