package com.example.easyfood.data.retrofit;

import com.example.easyfood.data.pojo.CategoryList;
import com.example.easyfood.data.pojo.MealsByCategoryList;
import com.example.easyfood.data.pojo.MealList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApi {

    @GET("random.php")
    Call<MealList> getRandomMeal();

    @GET("lookup.php")
    Call<MealList> getMealDetails(@Query("i") String id);

    @GET("filter.php")
    Call<MealsByCategoryList> getPopularItems(@Query("c") String categoryName);

    @GET("categories.php")
    Call<CategoryList> getCategories();

    @GET("filter.php")
    Call<MealsByCategoryList> getMealsByCategory(@Query("c") String categoryName);

    @GET("search.php")
    Call<MealList> searchMeals(@Query("s") String searchQuery);
}
