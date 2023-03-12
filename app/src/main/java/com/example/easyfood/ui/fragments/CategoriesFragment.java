package com.example.easyfood.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.easyfood.adapters.CategoriesAdapter;
import com.example.easyfood.data.pojo.CategoryList.Category;
import com.example.easyfood.databinding.FragmentCategoriesBinding;
import com.example.easyfood.mvvm.HomeViewModel;
import com.example.easyfood.ui.activities.CategoryMealsActivity;
import com.example.easyfood.ui.activities.MainActivity;

public class CategoriesFragment extends Fragment implements CategoriesAdapter.OnItemClickListener{

    private FragmentCategoriesBinding binding;
    private CategoriesAdapter categoriesAdapter;
    private HomeViewModel viewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ((MainActivity) requireActivity()).getViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prepareRecyclerView();
        observerCategoriesOfMeals();
    }

    private void observerCategoriesOfMeals() {
        viewModel.observeCategoriesMeal().observe(getViewLifecycleOwner(), categories->{
            categoriesAdapter.setCategoryList(categories);
        });
    }

    private void prepareRecyclerView() {

        binding.recyclerCategories.setLayoutManager(new GridLayoutManager(getActivity(), 2,
                GridLayoutManager.VERTICAL, false));

        categoriesAdapter = new CategoriesAdapter(this);

        binding.recyclerCategories.setAdapter(categoriesAdapter);
    }

    @Override
    public void onItemClickListener(Category category) {
        Intent intent = new Intent(getActivity(), CategoryMealsActivity.class);
        intent.putExtra(HomeFragment.MEAL_NAME, category.getStrCategory());
        startActivity(intent);
    }
}
