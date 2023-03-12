package com.example.easyfood.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.easyfood.data.pojo.UserTable;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertDetails(UserTable userTable);

    @Query("select * from User")
    LiveData<List<UserTable>> getDetails();

    @Query("delete from User")
    void deleteAllData();
}
