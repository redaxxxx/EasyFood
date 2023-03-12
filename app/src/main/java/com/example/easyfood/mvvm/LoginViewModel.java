package com.example.easyfood.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easyfood.Repository;
import com.example.easyfood.data.pojo.UserTable;

import java.io.Closeable;
import java.util.List;

public class LoginViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<UserTable>> getAllUser;

    public LoginViewModel(@NonNull Application application){
        super(application);
        repository = new Repository(application);
        getAllUser = repository.getAllUser();
    }

    public void insertUser(UserTable userTable){
        repository.insertUser(userTable);
    }

    public LiveData<List<UserTable>> getGetAllUser(){
        return getAllUser;
    }

    
}
