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

    // a list of falls
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FallEntity> falls);


    // this query return newest falls that sorted by dates
    @Query("SELECT * FROM falls ORDER BY date DESC")
    LiveData<List<FallEntity>> getAll();


}
