package io.xbird.freefalldetection.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import io.xbird.freefalldetection.R;
import io.xbird.freefalldetection.database.AppRepository;
import io.xbird.freefalldetection.database.FallEntity;


public class FallDetectionService extends Service implements SensorEventListener {

    //logt
    private static final String TAG = "FallDetectionService";
    private SensorManager mSensorManager;
    private Sensor accelerometer;

    public static boolean isLastStateFall = false;
    public static boolean isCurrentStateFall = false;
    public static long startTime = 0;
    public static long stopTime = 0;

    private AppRepository appRepository = AppRepository.getInstance(this);
    //private MainViewModel mainViewModel;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: Initializing Sensor Services");
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (mSensorManager != null) {
            accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
            Log.d(TAG, "onStartCommand: Registered accelerometer listener");
        }

        return Service.START_STICKY;

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        isCurrentStateFall = isFall(sensorEvent);

        if (!isLastStateFall && isCurrentStateFall) {
            Log.d(TAG, "ABOUZAR : FALL START");
            startTime = sensorEvent.timestamp;

        }

        if (isLastStateFall && !isCurrentStateFall) {
            Log.d(TAG, "ABOUZAR : FALL END");
            stopTime = sensorEvent.timestamp;
            Log.d(TAG, "ABOUZAR : fall time" + " " + ((stopTime - startTime) / 1000000) + " milliSeconds");
            String durationTime = ((stopTime - startTime) / 1000000) + " milliSeconds";
            sendLocalPushNotif();
            saveInDatabase(durationTime);

        }

        isLastStateFall = isCurrentStateFall;

    }

    private void saveInDatabase(String durationTime) {
        appRepository.insertFall(new FallEntity(durationTime));
        //mainViewModel.saveNote(durationTime);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public boolean isFall(SensorEvent sensorEvent) {

        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        float r = (float) Math.sqrt(x * x + y * y + z * z);

        return r < 0.5;
    }

    private void sendLocalPushNotif() {

        Log.d(TAG, "sendLocalPushNotif: ");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());

    }



    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = "io.xbird.freefalldetection";
        String channelName = "My Background Service";
        NotificationChannel chan = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        }

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(chan);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Free Fall Happened")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }


}
