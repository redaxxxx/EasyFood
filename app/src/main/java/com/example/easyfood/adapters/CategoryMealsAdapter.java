package com.example.easyfood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easyfood.data.pojo.CategoryList;
import com.example.easyfood.data.pojo.Meal;
import com.example.easyfood.data.pojo.MealsByCategoryList.MealsByCategory;
import com.example.easyfood.databinding.CategoryListItemBinding;
import com.example.easyfood.databinding.FavoriteItemBinding;
import com.example.easyfood.databinding.MealListItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryMealsAdapter extends RecyclerView.Adapter<CategoryMealsAdapter.CategoryViewHolder> {

    private OnItemClickListener onItemClickListener;

    public CategoryMealsAdapter(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    DiffUtil.ItemCallback<MealsByCategory> diffUtil = new DiffUtil.ItemCallback<MealsByCategory>() {
        @Override
        public boolean areItemsTheSame(@NonNull MealsByCategory oldItem, @NonNull MealsByCategory newItem) {
            return oldItem.getidMeal().equals(newItem.getidMeal());
        }

        @Override
        public boolean areContentsTheSame(@NonNull MealsByCategory oldItem, @NonNull MealsByCategory newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

    public AsyncListDiffer differ = new AsyncListDiffer(this, diffUtil);

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(MealListItemBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        MealsByCategory mealsByCategory = (MealsByCategory) differ.getCurrentList().get(position);
        Glide.with(holder.itemView)
                .load(mealsByCategory.getStrMealThumb())
                .into(holder.mealListItemBinding.imgMeal);

        holder.mealListItemBinding.mealName.setText(mealsByCategory.getStrMeal());

        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onClickItem((MealsByCategory) differ.getCurrentList().get(position));
        });
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
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
