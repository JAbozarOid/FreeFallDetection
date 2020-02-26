package io.xbird.freefalldetection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import io.xbird.freefalldetection.Service.FallDetectionService;
import io.xbird.freefalldetection.database.AppRepository;
import io.xbird.freefalldetection.database.FallEntity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<FallEntity> fallEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d(TAG, "onCreate: Service Called");
        Intent serviceIntent = new Intent(this, FallDetectionService.class);
        startService(serviceIntent);

        StringBuilder sensorText = new StringBuilder();

        fallEntities = AppRepository.getInstance(this).getAllNotes();
        if (fallEntities.size() > 0) {
            for (FallEntity entity : fallEntities) {
                sensorText.append(entity.getDuration()).append(
                        System.getProperty("line.separator"));
            }

            TextView textView = findViewById(R.id.tv_fall_list);
            textView.setText(sensorText);
        }


    }

}
