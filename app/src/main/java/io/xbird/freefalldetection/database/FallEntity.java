package io.xbird.freefalldetection.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "falls")
public class FallEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @Ignore
    private Date date; // sqlite can not store pure java so Date must convert to long integer -> please refer to DateConverter class
    private String duration;

    @Ignore
    public FallEntity() {
    }

    @Ignore
    public FallEntity(int id,String durationTime) {
        this.id = id;
        this.duration = durationTime;
    }

    public FallEntity(String duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String durationTime) {
        this.duration = durationTime;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", duration='" + duration + '\'' +
                '}';
    }
}
