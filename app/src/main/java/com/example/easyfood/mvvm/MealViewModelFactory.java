package com.example.easyfood.mvvm;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.easyfood.data.db.MealDatabase;

public class MealViewModelFactory implements ViewModelProvider.Factory {

    private final MealDatabase mealDatabase;
    public MealViewModelFactory(MealDatabase mealDatabase){

        this.mealDatabase = mealDatabase;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MealViewModel(mealDatabase);
    }
}
