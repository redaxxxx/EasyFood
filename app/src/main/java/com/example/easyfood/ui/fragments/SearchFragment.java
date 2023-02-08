package com.example.easyfood.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyfood.R;
import com.example.easyfood.adapters.MealsAdapter;
import com.example.easyfood.data.pojo.Meal;
import com.example.easyfood.databinding.FragmentSearchBinding;
import com.example.easyfood.mvvm.HomeViewModel;
import com.example.easyfood.ui.activities.MainActivity;
import com.example.easyfood.ui.activities.MealActivity;


public class SearchFragment extends Fragment implements MealsAdapter.OnItemCLickListener{

    private FragmentSearchBinding binding;
    private HomeViewModel viewModel;
    private MealsAdapter mealsAdapter;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ((MainActivity) requireActivity()).getViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        prepareRecyclerView();

        searchMeals();

        observerSearchMeals();


    }

    private void observerSearchMeals() {
        viewModel.observeSearchedMeal().observe(getViewLifecycleOwner(), meals -> {
            mealsAdapter.differ.submitList(meals);
        });
    }

    private void searchMeals() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchedMeal(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.searchedMeal(newText);
                return false;
            }
        });
    }

    private void prepareRecyclerView() {
        binding.searchMealsRecView.setLayoutManager(new GridLayoutManager(getContext(), 2
        , GridLayoutManager.VERTICAL, false));

        mealsAdapter = new MealsAdapter(this);
        binding.searchMealsRecView.setAdapter(mealsAdapter);
    }

    @Override
    public void onItemClick(Meal meal) {
        Intent intent = new Intent(getContext(), MealActivity.class);
        intent.putExtra(HomeFragment.MEAL_ID, meal.getIdMeal());
        intent.putExtra(HomeFragment.MEAL_NAME, meal.getStrMeal());
        intent.putExtra(HomeFragment.MEAL_THUMB, meal.getStrMealThumb());

        startActivity(intent);
    }
}