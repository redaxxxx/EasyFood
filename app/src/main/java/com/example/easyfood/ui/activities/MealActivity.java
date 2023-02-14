package com.example.easyfood.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.easyfood.R;
import com.example.easyfood.data.pojo.Meal;
import com.example.easyfood.databinding.ActivityMealBinding;
import com.example.easyfood.data.db.MealDatabase;
import com.example.easyfood.mvvm.MealViewModel;
import com.example.easyfood.mvvm.MealViewModelFactory;
import com.example.easyfood.ui.fragments.AboutFragment;
import com.example.easyfood.ui.fragments.HomeFragment;
import com.example.easyfood.ui.fragments.IngredientsFragment;
import com.example.easyfood.ui.fragments.InstructionsFragment;
import com.google.android.material.tabs.TabLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class MealActivity extends AppCompatActivity {

    private ActivityMealBinding mealBinding;
    private Meal mealToInsert = null;
    private String mealId;
    private String mealName;
    private String mealThumb;

    private final Bundle bundle = new Bundle();
    private String mealInstructions;
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

        // Setup TabLayout with viewpager

        initViews();

        viewModel.getMealDetails(mealId);

        observerDetailsMeal();

        setupViewpager();

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

    private Bundle sendIdMealToViewpagerFrags( ){
        bundle.putString(HomeFragment.MEAL_ID, mealId);
        return bundle;
    }

    private void setupViewpager(){
        mealBinding.tabLayout.addTab(mealBinding.tabLayout.newTab().setText("Instructions"));
        mealBinding.tabLayout.addTab(mealBinding.tabLayout.newTab().setText("Ingredients"));
        mealBinding.tabLayout.addTab(mealBinding.tabLayout.newTab().setText("AboutMeals"));
        mealBinding.viewPager.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(),getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Fragment fragment;
                    if (position == 0) {
                        fragment = new InstructionsFragment();
                        fragment.setArguments(sendIdMealToViewpagerFrags());
                        return fragment;
                    } else if (position == 1) {
                        fragment = new IngredientsFragment();
                        fragment.setArguments(sendIdMealToViewpagerFrags());
                        return fragment;
                    } else if (position == 2){
                        fragment = new AboutFragment();
                        fragment.setArguments(sendIdMealToViewpagerFrags());
                        return fragment;
                    } else {
                        return null;
                    }
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });

        mealBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mealBinding.viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        mealBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mealBinding.tabLayout.selectTab(mealBinding.tabLayout.getTabAt(position));
            }
        });
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

//                mealBinding.instructionStepsTv.setText(meal.getStrInstructions());
                youtubeLink = meal.getStrYoutube();

            }
        });

        viewModel.getProgressBarVisibility().observe(this, visibility->mealBinding.progressBar.setVisibility(visibility));
        viewModel.getDetailsViewVisibility().observe(this, visibility->mealBinding.detailsView.setVisibility(visibility));
        viewModel.getAddToFavoriteVisibility().observe(this, visibility->mealBinding.addToFavorite.setVisibility(visibility));
        viewModel.getYoutubeImgViewVisibility().observe(this, visibility->mealBinding.imgYoutube.setVisibility(visibility));
    }
}