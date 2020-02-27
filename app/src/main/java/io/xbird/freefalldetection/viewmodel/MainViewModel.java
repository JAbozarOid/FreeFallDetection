package io.xbird.freefalldetection.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.xbird.freefalldetection.database.AppRepository;
import io.xbird.freefalldetection.database.FallEntity;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<FallEntity>> mFalls;
    public MutableLiveData<FallEntity> mLiveFall = new MutableLiveData<>();

    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mFalls = mRepository.mfalls;
    }



    public void saveNote(String noteText) {
        FallEntity note = mLiveFall.getValue();
        if (note == null){
            if (TextUtils.isEmpty(noteText.trim())){
                return;
            }else{
                note = new FallEntity(noteText.trim());
            }
        }else{
            note.setDuration(noteText.trim());
        }
        mRepository.insertFall(note);
    }
}
