package com.example.android.mydiary.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * Created by OKUNIYI MONSURU on 6/27/2018.
 */

@Database(entities = {DiaryEntry.class}, version = 5, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "journal";
    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        //this should be remove later on. they shoouldn't run on the main thread
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract DiaryDao diaryDao();
}
