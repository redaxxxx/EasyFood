package com.example.easyfood.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.easyfood.R;
import com.example.easyfood.adapters.CategoriesAdapter;
import com.example.easyfood.adapters.MostPopularAdapter;
import com.example.easyfood.data.pojo.CategoryList.Category;
import com.example.easyfood.data.pojo.MealsByCategoryList.MealsByCategory;
import com.example.easyfood.data.pojo.Meal;
import com.example.easyfood.databinding.FragmentHomeBinding;
import com.example.easyfood.mvvm.HomeViewModel;
import com.example.easyfood.ui.activities.CategoryMealsActivity;
import com.example.easyfood.ui.activities.MainActivity;
import com.example.easyfood.ui.activities.MealActivity;
import com.example.easyfood.ui.fragments.bottomsheet.MealBottomSheetFragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements MostPopularAdapter.OnItemClickListener,
CategoriesAdapter.OnItemClickListener{

    private static final String LOG_TAG = HomeFragment.class.getSimpleName();
    private FragmentHomeBinding binding;

    private HomeViewModel viewModel;
    public static final String MEAL_ID = "com.example.easyfood.ui.fragments.mealId";
    public static final String MEAL_NAME = "com.example.easyfood.ui.fragments.mealName";
    public static final String MEAL_THUMB = "com.example.easyfood.ui.fragments.mealThumb";

    public static final String CATEGORY_NAME = "com.example.easyfood.ui.fragments.categoryName";
    private Meal randomMeal;

    private CategoriesAdapter categoryAdapter;
    private MostPopularAdapter popularAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ((MainActivity) requireActivity()).getViewModel();

        popularAdapter = new MostPopularAdapter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preparePopularRecyclerView();
        prepareCategoryRecyclerView();

        observerRandomMeal();
        randomMealCLick();

        viewModel.getPopularMeal();
        observerPopularMeal();

        viewModel.getCategoriesMeals();
        observerCategoriesMeal();

        searchIconClick();

    }

    private void searchIconClick() {
        binding.imgSearch.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_searchFragment);
        });
    }

    private void prepareCategoryRecyclerView() {
        binding.recViewCategories.setLayoutManager(new GridLayoutManager(getActivity(), 3,
                GridLayoutManager.VERTICAL, false));

        categoryAdapter = new CategoriesAdapter(this);

        binding.recViewCategories.setAdapter(categoryAdapter);
    }

    private void preparePopularRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,
                false);

        binding.recViewMealPopular.setLayoutManager(layoutManager);
        popularAdapter = new MostPopularAdapter(this);
        binding.recViewMealPopular.setAdapter(popularAdapter);
    }
    private void randomMealCLick() {
        binding.randomMealCard.setOnClickListener(view->{
            Intent intent = new Intent(getActivity(), MealActivity.class);
            intent.putExtra(MEAL_ID, randomMeal.getIdMeal());
            intent.putExtra(MEAL_NAME, randomMeal.getStrMeal());
            intent.putExtra(MEAL_THUMB, randomMeal.getStrMealThumb());
            startActivity(intent);
        });
    }

    private void observerRandomMeal(){
        viewModel.observeRandomMeal().observe(getViewLifecycleOwner(), new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {
                Log.d(LOG_TAG, "url of meal image " + meal.getStrMealThumb());
                Glide.with(HomeFragment.this)
                        .load(meal.getStrMealThumb())
                        .into(binding.imgRandomMeal);
                randomMeal = meal;
            }
        });
    }

    private void observerPopularMeal() {
        viewModel.observePopularMeal().observe(getViewLifecycleOwner(), mealsList -> {
            popularAdapter.setMeals((ArrayList<MealsByCategory>) mealsList);
        });
    }

    private void observerCategoriesMeal(){
        viewModel.observeCategoriesMeal().observe(getViewLifecycleOwner(), categories -> {
            categoryAdapter.setCategoryList(categories);
        });
    }

    @Override
    public void onItemListener(MealsByCategory meal) {
        Intent intent = new Intent(getActivity(), MealActivity.class);

        intent.putExtra(MEAL_ID, meal.getidMeal());
        intent.putExtra(MEAL_NAME, meal.getStrMeal());
        intent.putExtra(MEAL_THUMB, meal.getStrMealThumb());

        startActivity(intent);
    }

    @Override
    public void onLongItemListener(MealsByCategory meal) {
        MealBottomSheetFragment bottomSheetFragment = MealBottomSheetFragment.newInstance(meal.getidMeal());
        bottomSheetFragment.show(getChildFragmentManager(), "Meal Info");
    }

    @Override
    public void onItemClickListener(Category category) {
        Intent intent = new Intent(getActivity(), CategoryMealsActivity.class);
        intent.putExtra(MEAL_NAME, category.getStrCategory());
        startActivity(intent);
    }
}
