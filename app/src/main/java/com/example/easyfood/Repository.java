package com.example.easyfood;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.easyfood.data.db.MealDatabase;
import com.example.easyfood.data.db.UserDao;
import com.example.easyfood.data.pojo.UserTable;
import com.example.easyfood.executors.AppExecutors;
import com.example.easyfood.ui.activities.LoginActivity;

import java.util.List;

public class Repository {
    private UserDao userDao;
    private LiveData<List<UserTable>> allUser;

    public Repository(Application application){
        MealDatabase db = MealDatabase.getInstance(application);
        userDao = db.userDao();
        allUser = userDao.getDetails();
    }

    public LiveData<List<UserTable>> getAllUser(){
        return allUser;
    }

    public void insertUser(UserTable userTable){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDao.insertDetails(userTable);
            }
        });

    }

//    private static class LoginInsertion extends AsyncTask<UserTable, Void, Void>{
//
//        private UserDao mUserDao;
//        public LoginInsertion(UserDao userDao){
//            mUserDao = userDao;
//        }
//        @Override
//        protected Void doInBackground(UserTable... userTables) {
//            mUserDao.insertDetails(userTables[0]);
//            return null;
//        }
//    }
}
