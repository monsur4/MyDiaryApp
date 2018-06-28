package com.example.android.mydiary.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by OKUNIYI MONSURU on 6/27/2018.
 */

public class DateConverter {
    /**
     * This method will be used by room to write a date to the database
     */
    @TypeConverter
    public static Long toTimeStamp(Date date){
        return date == null ? null : date.getTime();
    }

    /**
     * This method will be used by room to read a date from the database
     */
    @TypeConverter
    public static Date toDate(Long timeStamp){
        return timeStamp == null ? null : new Date(timeStamp);
    }
}
