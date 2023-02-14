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
import com.example.easyfood.databinding.FragmentAboutBinding;
import com.example.easyfood.mvvm.MealViewModel;
import com.example.easyfood.mvvm.MealViewModelFactory;


public class AboutFragment extends Fragment {

    private FragmentAboutBinding binding;
    private MealDatabase mDB;
    private MealViewModel viewModel;

    private String mealId;
    public AboutFragment() {
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
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null){
            mealId = bundle.getString(HomeFragment.MEAL_ID);
        }
        viewModel.getMealDetails(mealId);
        observeMealAbout();
    }

    private void observeMealAbout(){
        viewModel.observeMealDetails().observe(getViewLifecycleOwner(), meal -> {
            binding.categoryTextView.setText("Category : "+meal.getStrCategory());
            binding.areaTv.setText("Area : "+meal.getStrArea());

        });
    }
}