package com.example.easyfood.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.easyfood.R;
import com.example.easyfood.data.db.MealDatabase;
import com.example.easyfood.mvvm.HomeViewModel;
import com.example.easyfood.mvvm.HomeViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MealDatabase mealDatabase;
    private HomeViewModelFactory factory;
    public HomeViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_nav);
        NavController navController = Navigation.findNavController(this, R.id.nav_host);

        //setup navigation controller with bottom navigation
        NavigationUI.setupWithNavController(bottomNavigation, navController);
    }

    public HomeViewModel getViewModel() {
        mealDatabase = MealDatabase.getInstance(this);
        factory = new HomeViewModelFactory(mealDatabase);
        viewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
        return viewModel;
    }
}