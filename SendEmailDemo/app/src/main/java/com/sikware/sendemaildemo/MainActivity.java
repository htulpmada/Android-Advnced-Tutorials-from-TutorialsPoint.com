package com.sikware.sendemaildemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startBtn = (Button) findViewById(R.id.sendEmail);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    protected void sendEmail(){
        Log.i("Send Email","");
        String[] TO = {"adampluth@yahoo.com"};
        String[] CC = {"apluth@crimson.ua.edu"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your Subject Goes Here!!!");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email maessage Goes Here!!!");

        try{
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending Email", "");
        }
        catch(android.content.ActivityNotFoundException ex){
            Toast.makeText(MainActivity.this, "There is no Email App installed", Toast.LENGTH_SHORT).show();
        }
    }

}
