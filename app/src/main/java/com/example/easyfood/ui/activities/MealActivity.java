package com.example.easyfood.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.easyfood.R;
import com.example.easyfood.data.pojo.Meal;
import com.example.easyfood.databinding.ActivityMealBinding;
import com.example.easyfood.data.db.MealDatabase;
import com.example.easyfood.mvvm.MealViewModel;
import com.example.easyfood.mvvm.MealViewModelFactory;
import com.example.easyfood.ui.fragments.HomeFragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MealActivity extends AppCompatActivity {

    private ActivityMealBinding mealBinding;

    private Meal mealToInsert = null;
    private String mealId;
    private String mealName;
    private String mealThumb;
    private String youtubeLink;
    private MealViewModel viewModel;

    private boolean isChecked;

    private MealDatabase mealDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mealBinding = ActivityMealBinding.inflate(getLayoutInflater());
        setContentView(mealBinding.getRoot());

        mealDatabase = MealDatabase.getInstance(this);

        MealViewModelFactory factory = new MealViewModelFactory(mealDatabase);
        viewModel = new ViewModelProvider(this, factory).get(MealViewModel.class);

        getMealInformationFromIntent();

        initViews();

        viewModel.getMealDetails(mealId);

        observerDetailsMeal();

        onYoutubeImgClick();

        buttonFavoriteClick();
    }

    private void initViews() {
        Glide.with(getApplicationContext())
                .load(mealThumb)
                .into(mealBinding.imgMealDetail);

        mealBinding.collapsingToolbar.setTitle(mealName);
        mealBinding.collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        mealBinding.collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));

    }

    private void buttonFavoriteClick(){
        mealBinding.addToFavorite.setOnClickListener(view -> {
            viewModel.insertMeal(mealToInsert);
            Toast.makeText(this, "Meal saved", Toast.LENGTH_SHORT).show();
        });
    }

    private void onYoutubeImgClick(){
        mealBinding.imgYoutube.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink));
            startActivity(intent);
        });
    }

    private void getMealInformationFromIntent() {
        Intent intent = getIntent();
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID);
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME);
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB);
    }

    private void observerDetailsMeal(){
        viewModel.observeMealDetails().observe(this, new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {
                mealToInsert = meal;
                mealBinding.categoryTextView.setText("Category : "+meal.getStrCategory());
                mealBinding.areaTv.setText("Area : "+meal.getStrArea());
                mealBinding.instructionStepsTv.setText(meal.getStrInstructions());
                youtubeLink = meal.getStrYoutube();

            }
        });

        viewModel.getProgressBarVisibility().observe(this, visibility->mealBinding.progressBar.setVisibility(visibility));
        viewModel.getDetailsViewVisibility().observe(this, visibility->mealBinding.detailsView.setVisibility(visibility));
        viewModel.getAddToFavoriteVisibility().observe(this, visibility->mealBinding.addToFavorite.setVisibility(visibility));
        viewModel.getYoutubeImgViewVisibility().observe(this, visibility->mealBinding.imgYoutube.setVisibility(visibility));
    }

}