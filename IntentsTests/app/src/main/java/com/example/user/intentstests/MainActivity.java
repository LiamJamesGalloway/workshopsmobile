package com.example.user.intentstests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.net.Uri;
import java.util.Calendar;
import android.provider.CalendarContract;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cameraIntent(View view){

        int REQUEST_IMAGE_CAPTURE = 1;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void runPermissions(View view) {

        // create an intent to start the activity called addPermissions
        Intent intent = new Intent(this, addPermissions.class);
        // run Permissions activity
        startActivity(intent);
    }

    public void browserIntent(View view) {
        String url = "http://www.imdb.com/title/";
        String movieID = "tt0816692";
        Uri webpage = Uri.parse(url + movieID);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);
    }

    public void eventIntent(View view) {
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2015, 12, 25, 00, 00);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2015, 12, 25, 00, 01);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Xmas!");
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "North Pole");
        startActivity(calendarIntent);
    }

    public void loadTweets(View view) {
        Intent intent = new Intent(this, Tweets.class);
        startActivity(intent);
    }

    public void getMusicData(View view) {

        // create an intent to start Music activity
        Intent intent = new Intent(this, Music.class);
        startActivity(intent);
    }
}
