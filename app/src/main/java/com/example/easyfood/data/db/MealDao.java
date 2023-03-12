package com.example.easyfood.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.easyfood.data.pojo.Meal;
import com.example.easyfood.data.pojo.UserTable;

import java.util.List;

@Dao
public interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(Meal meal);

    @Delete
    void deleteMeal(Meal meal);

    @Query("SELECT * FROM mealInformation")
    LiveData<Meal> getAllMeals();

    @Query("SELECT * FROM mealInformation")
    LiveData<List<Meal>> getAllDataOFMeals();

    @Query("SELECT * FROM mealInformation WHERE idMeal=:id ")
    LiveData<Meal> getMealById(String id);
}
