package com.example.easyfood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easyfood.data.pojo.MealsByCategoryList.MealsByCategory;
import com.example.easyfood.databinding.MealListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryMealsAdapter extends RecyclerView.Adapter<CategoryMealsAdapter.CategoryViewHolder> {

    private List<MealsByCategory> meals = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public CategoryMealsAdapter(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public void setCategoryMealsList(List<MealsByCategory> mealsList){
        meals = mealsList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(MealListItemBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Glide.with(holder.itemView)
                .load(meals.get(position).getStrMealThumb())
                .into(holder.mealListItemBinding.imgMeal);

        holder.mealListItemBinding.mealName.setText(meals.get(position).getStrMeal());

        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onClickItem(meals.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{

        MealListItemBinding mealListItemBinding;
        public CategoryViewHolder(@NonNull MealListItemBinding binding) {
            super(binding.getRoot());
            mealListItemBinding = binding;
        }
    }

    public interface OnItemClickListener{
        void onClickItem(MealsByCategory meal);
    }
}
