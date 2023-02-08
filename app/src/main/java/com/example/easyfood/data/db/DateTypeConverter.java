package com.example.easyfood.data.db;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

@TypeConverters
public class DateTypeConverter {

    @TypeConverter
    public static Date toDate(Long timeStamp){
        return timeStamp == null ? null : new Date(timeStamp);
    }

    @TypeConverter
    public static Long toTimeStamp(Date date){
        return date == null ? null : date.getTime();
    }
}
