package com.example.android.mydiary.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by OKUNIYI MONSURU on 6/27/2018.
 */
@Entity(tableName = "diary")
public class DiaryEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Date dateModified;
    private String details;

    /**
     * This constructor will be used to add new entries into the diary. Since the id will be auto-generated,
     * we therefore do not need to set the id
     */
    @Ignore
    public DiaryEntry(String title, String details, Date dateModified) {
        this.title = title;
        this.dateModified = dateModified;
        this.details = details;
    }

    /**
     * This constructor will be used by room to update previous entries within the diary. Since every updated entry
     * already has an id, room will need the id to update the contents of the entry
     */
    public DiaryEntry(int id, String title, Date dateModified, String details) {
        this.id = id;
        this.title = title;
        this.dateModified = dateModified;
        this.details = details;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateModified() {
        return dateModified;
    }
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
}
