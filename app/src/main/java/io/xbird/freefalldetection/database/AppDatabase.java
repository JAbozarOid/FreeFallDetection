package io.xbird.freefalldetection.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {FallEntity.class},version = 1,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "AppDatabase.db";

    // this field for support singleton -> volatile which means that it can only be reference from main memory
    private static volatile AppDatabase instance;

    // that is not allowed that two class refer to database at the same time
//    private static final Object LOCK = new Object();

    // this method is abstract because this method never call directly
    public abstract FallDAO fallDAO();

    public static AppDatabase getInstance(Context context) {
        if (instance == null){
//            synchronized (LOCK){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DATABASE_NAME).allowMainThreadQueries().build();
//                }
            }
        }
        return instance;
    }
}
