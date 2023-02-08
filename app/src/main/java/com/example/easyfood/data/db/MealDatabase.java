package com.example.easyfood.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.easyfood.data.pojo.Meal;

@Database(entities = {Meal.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class MealDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "meal.db";
    private static MealDatabase mInstance;

    public static MealDatabase getInstance(Context context){
        if (mInstance == null) {
            synchronized (LOCK){
                mInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        MealDatabase.class,
                        DATABASE_NAME
                ).fallbackToDestructiveMigration()
                        .build();
            }
        }

        return mInstance;
    }

    public abstract MealDao mealDao();
}
