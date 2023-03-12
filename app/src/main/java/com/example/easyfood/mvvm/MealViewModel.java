package com.example.easyfood.mvvm;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easyfood.R;
import com.example.easyfood.VerifyConnection;
import com.example.easyfood.data.pojo.Meal;
import com.example.easyfood.data.pojo.MealList;
import com.example.easyfood.data.db.MealDatabase;
import com.example.easyfood.executors.AppExecutors;
import com.example.easyfood.data.retrofit.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealViewModel extends ViewModel {

    private MutableLiveData<Meal> mealDetails = new MutableLiveData<>();
    private MutableLiveData<Integer> progressBarVisibility = new MutableLiveData<>();
    private MutableLiveData<Integer> addToFavoritesViewVisibility = new MutableLiveData<>();
    private MutableLiveData<Integer>  youtubeImgViewVisibility = new MutableLiveData<>();
    private MutableLiveData<Integer> detailsViewVisibility = new MutableLiveData<>();

    private MutableLiveData<Integer> checkboxFavoriteVisibility = new MutableLiveData<>();

    private MutableLiveData<Boolean> onCheckboxFavorite = new MutableLiveData<>();

    private MealDatabase mealDatabase;

    public MealViewModel(MealDatabase mealDatabase){
        this.mealDatabase = mealDatabase;
//        showLoading();
    }

    public void getMealDetails(String id){

        RetrofitInstance.api.getMealDetails(id).enqueue(new Callback<MealList>() {
            @Override
            public void onResponse(Call<MealList> call, Response<MealList> response) {
                if (response.body() != null){
                    Meal meal = response.body().getMeals().get(0);
                    mealDetails.postValue(meal);
                    showDetails();
                }
            }

            @Override
            public void onFailure(Call<MealList> call, Throwable t) {
                Log.d("MealActivity", t.getLocalizedMessage());
            }
        });
    }

    public LiveData<Integer> getProgressBarVisibility(){
        return progressBarVisibility;
    }
    public LiveData<Integer> getAddToFavoriteVisibility(){
        return addToFavoritesViewVisibility;
    }
    public LiveData<Integer> getYoutubeImgViewVisibility(){
        return youtubeImgViewVisibility;
    }
    public LiveData<Integer> getDetailsViewVisibility(){
        return detailsViewVisibility;
    }

    private void showLoading(){
        progressBarVisibility.setValue(View.VISIBLE);
        addToFavoritesViewVisibility.setValue(View.GONE);
        detailsViewVisibility.setValue(View.GONE);
        youtubeImgViewVisibility.setValue(View.GONE);
        checkboxFavoriteVisibility.setValue(View.GONE);
    }
    private void showDetails(){
        progressBarVisibility.setValue(View.GONE);
        addToFavoritesViewVisibility.setValue(View.VISIBLE);
        detailsViewVisibility.setValue(View.VISIBLE);
        youtubeImgViewVisibility.setValue(View.VISIBLE);
        checkboxFavoriteVisibility.setValue(View.VISIBLE);
    }

    public LiveData<Meal> observeMealDetails(){
        return mealDetails;
    }


    // insert meal to my database
    public void insertMeal(Meal meal){
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                mealDatabase.mealDao().upsert(meal);
            }
        });
    }

    public LiveData<Meal> getMealById(String id){
        return mealDatabase.mealDao().getMealById(id);
    }

}
