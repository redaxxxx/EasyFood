package com.example.easyfood.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyfood.data.db.MealDatabase;
import com.example.easyfood.databinding.FragmentIngredientsBinding;
import com.example.easyfood.mvvm.MealViewModel;
import com.example.easyfood.mvvm.MealViewModelFactory;


public class IngredientsFragment extends Fragment {

    private FragmentIngredientsBinding binding;
    private MealViewModel viewModel;
    private MealDatabase mDB;

    String idMeal;
    public IngredientsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDB = MealDatabase.getInstance(getActivity());
        MealViewModelFactory factory = new MealViewModelFactory(mDB);
        viewModel = new ViewModelProvider(this, factory).get(MealViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentIngredientsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
           idMeal =  bundle.getString(HomeFragment.MEAL_ID);
        }
        viewModel.getMealDetails(idMeal);
        observeMealIngredients();
    }


    private void observeMealIngredients(){
        viewModel.observeMealDetails().observe(getViewLifecycleOwner(), meal -> {
            binding.ingredient1Tv.setText(meal.getStrMeasure1() +" "+ meal.getStrIngredient1());
            binding.ingredient2Tv.setText(meal.getStrMeasure2() +" "+ meal.getStrIngredient2());
            binding.ingredient3Tv.setText(meal.getStrMeasure3() +" "+ meal.getStrIngredient3());
            binding.ingredient4Tv.setText(meal.getStrMeasure4() +" "+ meal.getStrIngredient4());
            binding.ingredient5Tv.setText(meal.getStrMeasure5() +" "+ meal.getStrIngredient5());
            binding.ingredient6Tv.setText(meal.getStrMeasure6() +" "+ meal.getStrIngredient6());
            binding.ingredient7Tv.setText(meal.getStrMeasure7() +" "+ meal.getStrIngredient7());
            binding.ingredient8Tv.setText(meal.getStrMeasure8() +" "+ meal.getStrIngredient8());
            binding.ingredient9Tv.setText(meal.getStrMeasure9() +" "+ meal.getStrIngredient9());

        });
    }

}