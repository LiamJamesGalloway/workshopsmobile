package com.example.user.intentstests;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.widget.Toast;
import android.view.View;

public class addPermissions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_permissions);
    }

    public void cameraPermissions(View view) {

        ActivityCompat.requestPermissions(addPermissions.this,
                new String[]{Manifest.permission.CAMERA},
                1);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the work!
                    // display short notification stating permission granted
                    Toast.makeText(addPermissions.this, "Permission granted for camera!", Toast.LENGTH_SHORT).show();

                } else {

                    // permission denied, you need to add code to deal with this!
                }
                return;
            }
        }
    }
}