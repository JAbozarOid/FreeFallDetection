package io.xbird.freefalldetection.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FallDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFall(FallEntity fallEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FallEntity> falls);


    @Query("SELECT * FROM falls")
    LiveData<List<FallEntity>> getAll();


}
