package io.xbird.freefalldetection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import io.xbird.freefalldetection.Service.FallDetectionService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d(TAG, "onCreate: Service Called");
        Intent serviceIntent = new Intent(this, FallDetectionService.class);
        startService(serviceIntent);

    }

}
