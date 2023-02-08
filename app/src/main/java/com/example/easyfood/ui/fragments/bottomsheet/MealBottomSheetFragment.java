package com.example.easyfood.ui.fragments.bottomsheet;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.easyfood.R;
import com.example.easyfood.data.pojo.Meal;
import com.example.easyfood.databinding.FragmentMealBottomSheetBinding;
import com.example.easyfood.mvvm.HomeViewModel;
import com.example.easyfood.ui.activities.MainActivity;
import com.example.easyfood.ui.activities.MealActivity;
import com.example.easyfood.ui.fragments.HomeFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealBottomSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealBottomSheetFragment extends BottomSheetDialogFragment {

    private FragmentMealBottomSheetBinding binding;

    private HomeViewModel viewModel;
    private static final String MEAL_ID = "param1";

    private String mealId;
    private String mealName;
    private String mealThumb;

    public MealBottomSheetFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MealBottomSheetFragment.
     */
    public static MealBottomSheetFragment newInstance(String param1) {
        MealBottomSheetFragment fragment = new MealBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(MEAL_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mealId = getArguments().getString(MEAL_ID);
        }
        viewModel = ((MainActivity) requireActivity()).getViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMealBottomSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getMealById(mealId);
        observerBottomSheetMeal();
        bottomSheetOnClick();
    }

    private void bottomSheetOnClick() {
        binding.bottomSheet.setOnClickListener(view -> {
            if (mealName != null & mealThumb != null){
                Intent intent = new Intent(getActivity(), MealActivity.class);
                intent.putExtra(HomeFragment.MEAL_ID, mealId);
                intent.putExtra(HomeFragment.MEAL_NAME, mealName);
                intent.putExtra(HomeFragment.MEAL_THUMB, mealThumb);
                startActivity(intent);
            }
        });
    }

    private void observerBottomSheetMeal() {
        viewModel.observeBottomSheetMeal().observe(getViewLifecycleOwner(), new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {
                Glide.with(MealBottomSheetFragment.this)
                        .load(meal.getStrMealThumb())
                        .into(binding.imgBottomSheet);

                binding.areaBottomSheetTv.setText(meal.getStrArea());
                binding.categoryBottomSheetTv.setText(meal.getStrCategory());
                binding.bottomSheetMealName.setText(meal.getStrMeal());

                mealName = meal.getStrMeal();
                mealThumb = meal.getStrMealThumb();
            }
        });
    }
}