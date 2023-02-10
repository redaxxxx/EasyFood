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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyfood.adapters.FavoriteAdapter;
import com.example.easyfood.adapters.MealsAdapter;
import com.example.easyfood.data.pojo.Meal;
import com.example.easyfood.databinding.FragmentFavoritesBinding;
import com.example.easyfood.mvvm.HomeViewModel;
import com.example.easyfood.ui.activities.MainActivity;
import com.example.easyfood.ui.activities.MealActivity;
import com.google.android.material.snackbar.Snackbar;

public class FavoritesFragment extends Fragment implements FavoriteAdapter.OnFavoriteItemClickListener {

    private HomeViewModel viewModel;
    private FavoriteAdapter mFavoriteAdapter;

    public static final String MEAL_ID = "com.example.easyfood.ui.fragments.mealId";
    public static final String MEAL_NAME = "com.example.easyfood.ui.fragments.mealName";
    public static final String MEAL_THUMB = "com.example.easyfood.ui.fragments.mealThumb";
    private FragmentFavoritesBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ((MainActivity) requireActivity()).getViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prepareRecyclerView();
        observeFavoriteMeals();

        // Delete item by swiping
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Meal meal = (Meal) mFavoriteAdapter.differ.getCurrentList().get(position);
                viewModel.deleteMeal(meal);
                mFavoriteAdapter.notifyItemRemoved(position);

                Snackbar.make(requireView(), "Meal delete", Snackbar.LENGTH_SHORT).setAction(
                        "Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                viewModel.insertMeal(meal);
                            }
                        }
                ).show();
            }
        }).attachToRecyclerView(binding.recFavorites);
    }

    private void prepareRecyclerView() {
        binding.recFavorites.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false));

        mFavoriteAdapter = new FavoriteAdapter(this);

        binding.recFavorites.setAdapter(mFavoriteAdapter);
    }

    private void observeFavoriteMeals() {
        viewModel.observeFavoriteMeals().observe(getViewLifecycleOwner(), meals -> {
            mFavoriteAdapter.differ.submitList(meals);
        });
    }

    //When i click on item

    @Override
    public void onClickListener(Meal meal) {
        Intent intent = new Intent(getActivity(), MealActivity.class);

        intent.putExtra(MEAL_ID, meal.getIdMeal());
        intent.putExtra(MEAL_NAME, meal.getStrMeal());
        intent.putExtra(MEAL_THUMB, meal.getStrMealThumb());

        startActivity(intent);
    }
}
