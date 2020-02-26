package io.xbird.freefalldetection.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

public class AppRepository {

    //private static final AppRepository ourInstance = new AppRepository(); // instantiated of a class

    // "*in order to pass context to room library ourInstance must changed*"
    private static AppRepository ourInstance;
    public List<FallEntity> mNotes; // here just declare it and in the constructor we instantiated
    private AppDatabase mDb; // in order to access to db we need access to context so we must refactor some class and pass context to room library -> "*so ourInstance must change*"

    /**
     * for handle room database operation execute in background thread
     * it is for making sure that "not run" multiple database operation at the same time
     * by this executor we sure all execute run after other
     */
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null){
            ourInstance = new AppRepository(context); // so we pass Context as a parameter to AppRepository method
        }
        return ourInstance;
    }

    // constructor
    private AppRepository(Context context) {
        // *** the order of this codes are important
        mDb = AppDatabase.getInstance(context); // now we have a reference to actual database and now ready to insert the data
        mNotes = getAllNotes();
    }

    // *** this method is for AppRepository to know where the data come from, from local or remote
    // *** this method return all data in table notes
    public List<FallEntity> getAllNotes(){
        return mDb.fallDAO().getAll();
    }

    public void insertNote(final FallEntity note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.fallDAO().insertFall(note);
            }
        });
    }
}
