package io.xbird.freefalldetection.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

public class AppRepository {

    private static AppRepository ourInstance;
    public LiveData<List<FallEntity>> mfalls;
    private AppDatabase mDb;

    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null){
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mfalls = getAllFalls();
    }

    public LiveData<List<FallEntity>> getAllFalls(){
        return mDb.fallDAO().getAll();
    }

    public void insertFall(final FallEntity fall) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.fallDAO().insertFall(fall);
            }
        });
    }
}
