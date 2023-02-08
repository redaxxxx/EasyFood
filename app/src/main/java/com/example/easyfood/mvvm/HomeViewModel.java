package com.example.easyfood.mvvm;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easyfood.data.pojo.CategoryList;
import com.example.easyfood.data.pojo.CategoryList.Category;
import com.example.easyfood.data.pojo.MealsByCategoryList;
import com.example.easyfood.data.pojo.MealsByCategoryList.MealsByCategory;
import com.example.easyfood.data.pojo.Meal;
import com.example.easyfood.data.pojo.MealList;
import com.example.easyfood.data.db.MealDatabase;
import com.example.easyfood.data.retrofit.RetrofitInstance;
import com.example.easyfood.executors.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<Meal> mRandomMeal = new MutableLiveData<>();
    private MutableLiveData<List<MealsByCategory>> mPopularItems = new MutableLiveData<>();

    private MealDatabase mealDatabase;

    private  LiveData<List<Meal>> favoritesMealsLiveData;

    private MutableLiveData<List<Category>> mCategoryItems = new MutableLiveData<>();

    private MutableLiveData<Meal> mBottomSheetMeal = new MutableLiveData<>();

    private MutableLiveData<List<Meal>> mSearchedMeal = new MutableLiveData<>();

    public HomeViewModel(MealDatabase mealDatabase){
        this.mealDatabase = mealDatabase;
        getRandomMeal();
    }

    public void getRandomMeal(){

        RetrofitInstance.api.getRandomMeal().enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.body() != null){
                    Meal randomMeal = response.body().getMeals().get(0);
                    mRandomMeal.postValue(randomMeal);
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable t) {
                Log.d("Home Fragment", t.getLocalizedMessage());
            }
        });
    }

    public void getPopularMeal(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(new Callback<MealsByCategoryList>() {
            @Override
            public void onResponse(Call<MealsByCategoryList> call, Response<MealsByCategoryList> response) {
                if (response.body() != null){
                    mPopularItems.postValue(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealsByCategoryList> call, Throwable t) {
                Log.d("Home Fragment", t.getLocalizedMessage());
            }
        });
    }

    public void getCategoriesMeals(){
        RetrofitInstance.api.getCategories().enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                if (response.body() != null){
                    mCategoryItems.postValue(response.body().getCategories());
                }
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {
                Log.d("Home Fragment", t.getLocalizedMessage());
            }
        });
    }

    public void getMealById(String id){
        RetrofitInstance.api.getMealDetails(id).enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.body() != null){
                    mBottomSheetMeal.postValue(response.body().getMeals().get(0));
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable t) {
                Log.d("Home Fragment", t.getLocalizedMessage());
            }
        });
    }

    public void searchedMeal(String searchQuery){
        RetrofitInstance.api.searchMeals(searchQuery).enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.body() != null){
                    mSearchedMeal.postValue(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable t) {
                Log.d("Home Fragment", t.getLocalizedMessage());
            }
        });
    }

    public LiveData<Meal> observeRandomMeal(){
        return mRandomMeal;
    }
    public LiveData<List<MealsByCategory>> observePopularMeal(){
        return mPopularItems;
    }

    public LiveData<List<Category>> observeCategoriesMeal(){
        return mCategoryItems;
    }

    public LiveData<List<Meal>> observeFavoriteMeals(){
        favoritesMealsLiveData = mealDatabase.mealDao().getAllDataOFMeals();
        return favoritesMealsLiveData;
    }

    public LiveData<Meal> observeBottomSheetMeal(){
        return mBottomSheetMeal;
    }

    public LiveData<List<Meal>> observeSearchedMeal(){
        return mSearchedMeal;
    }

    public void insertMeal(Meal meal){
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                mealDatabase.mealDao().upsert(meal);
            }
        });
    }

    public void deleteMeal(Meal meal){
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                mealDatabase.mealDao().deleteMeal(meal);
            }
        });
    }
}
