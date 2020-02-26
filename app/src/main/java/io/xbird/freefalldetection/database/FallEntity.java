package io.xbird.freefalldetection.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "falls")
public class FallEntity {

    @PrimaryKey(autoGenerate = true) // this means id can generate automatically
    private int id;
    private Date date; // sqlite can not store pure java so Date must convert to long integer -> please refer to DateConverter class
    private String duration;

    @Ignore
    // this annotation means Room can have only one constructor so this annotation ignore this and other constructor
    public FallEntity() {
    }

    public FallEntity(int id, Date date, String durationTime) {
        this.id = id;
        this.date = date;
        this.duration = durationTime;
    }

    @Ignore
    public FallEntity(Date date, String durationTime) {
        this.date = date;
        this.duration = durationTime;
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
