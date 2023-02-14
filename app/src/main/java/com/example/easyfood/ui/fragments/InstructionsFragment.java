package com.example.easyfood.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyfood.data.db.MealDatabase;
import com.example.easyfood.databinding.FragmentInstructionsBinding;
import com.example.easyfood.mvvm.MealViewModel;
import com.example.easyfood.mvvm.MealViewModelFactory;

public class InstructionsFragment extends Fragment {

    private FragmentInstructionsBinding binding;
    private MealViewModel viewModel;
    private MealDatabase mDB;

    private String mealId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDB = MealDatabase.getInstance(getActivity());
        MealViewModelFactory factory = new MealViewModelFactory(mDB);
        viewModel = new ViewModelProvider(requireActivity(), factory).get(MealViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInstructionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mealId = bundle.getString(HomeFragment.MEAL_ID);
        }
        else Log.d("Instructions Fragment", "Bundle is Empty");
        viewModel.getMealDetails(mealId);
        observeMealInstructions();
    }

    private void observeMealInstructions(){
        viewModel.observeMealDetails().observe(getViewLifecycleOwner(), meal -> {
            binding.instructionsTv.setText(meal.getStrInstructions());
        });
    }

}