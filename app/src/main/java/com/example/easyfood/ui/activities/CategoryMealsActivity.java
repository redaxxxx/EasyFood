package com.example.easyfood.ui.activities;
import com.example.easyfood.adapters.CategoryMealsAdapter;
import com.example.easyfood.data.pojo.MealsByCategoryList;
import com.example.easyfood.databinding.ActivityCategoryMealsBinding;
import com.example.easyfood.mvvm.CategoryMealsViewModel;
import com.example.easyfood.ui.fragments.HomeFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

public class CategoryMealsActivity extends AppCompatActivity implements CategoryMealsAdapter.OnItemClickListener {

    private ActivityCategoryMealsBinding binding;
    private CategoryMealsViewModel viewModel;
    private String categoryName;
    private CategoryMealsAdapter mealsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryMealsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prepareRecyclerView();
        getCategoriesInformationFromIntent();

        viewModel = ViewModelProviders.of(this).get(CategoryMealsViewModel.class);
        viewModel.getMealsByCategory(categoryName);

        observerCategoryMeals();
    }

    private void prepareRecyclerView() {
        binding.recMeals.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        mealsAdapter = new CategoryMealsAdapter(this);
        binding.recMeals.setAdapter(mealsAdapter);
    }

    private void getCategoriesInformationFromIntent(){
        Intent intent = getIntent();
        categoryName = intent.getStringExtra(HomeFragment.MEAL_NAME);
    }

    private void observerCategoryMeals(){
        viewModel.observeCategoryMeals().observe(this, mealList->{
            String size = String.valueOf(mealList.size());
            binding.categoryCountTv.setText( size +" "+ categoryName);
            mealsAdapter.setCategoryMealsList(mealList);
        });
    }


    @Override
    public void onClickItem(MealsByCategoryList.MealsByCategory meal) {
        Intent intent = new Intent(this, MealActivity.class);

        intent.putExtra(HomeFragment.MEAL_ID, meal.getidMeal());
        intent.putExtra(HomeFragment.MEAL_NAME, meal.getStrMeal());
        intent.putExtra(HomeFragment.MEAL_THUMB, meal.getStrMealThumb());

        startActivity(intent);
    }
}