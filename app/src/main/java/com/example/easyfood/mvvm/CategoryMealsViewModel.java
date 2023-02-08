package com.example.easyfood.mvvm;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easyfood.data.pojo.MealsByCategoryList;
import com.example.easyfood.data.pojo.MealsByCategoryList.MealsByCategory;
import com.example.easyfood.data.retrofit.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryMealsViewModel extends ViewModel {

    private MutableLiveData<List<MealsByCategory>> mCategoryMeals = new MutableLiveData<>();
    public CategoryMealsViewModel(){

    }

    public void getMealsByCategory(String categoryName) {
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(new Callback<MealsByCategoryList>() {
            @Override
            public void onResponse(Call<MealsByCategoryList> call, Response<MealsByCategoryList> response) {
                if (response.body() != null){
                    mCategoryMeals.postValue(response.body().getMeals());
                }
                else return;
            }

            @Override
            public void onFailure(Call<MealsByCategoryList> call, Throwable t) {
                Log.d("Category Meals Activity", t.getLocalizedMessage());
            }
        });
    }

    public LiveData<List<MealsByCategory>> observeCategoryMeals(){
        return mCategoryMeals;
    }
}
