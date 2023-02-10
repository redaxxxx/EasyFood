package com.example.easyfood.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easyfood.data.pojo.Meal;
import com.example.easyfood.databinding.FavoriteItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    OnFavoriteItemClickListener onItemClickListener;
    public FavoriteAdapter(OnFavoriteItemClickListener onFavoriteItemClickListener){
        onItemClickListener = onFavoriteItemClickListener;
    }
    DiffUtil.ItemCallback<Meal> diffUtil = new DiffUtil.ItemCallback<Meal>() {
        @Override
        public boolean areItemsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
            return oldItem.getIdMeal().equals(newItem.getIdMeal());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Meal oldItem, @NonNull Meal newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

    public AsyncListDiffer differ = new AsyncListDiffer(this, diffUtil);

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteViewHolder(FavoriteItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Meal meal = (Meal) differ.getCurrentList().get(position);
        Glide.with(holder.itemView)
                .load(meal.getStrMealThumb())
                .into(holder.binding.imgFavoriteMeal);

        holder.binding.favoriteMealNameTv.setText(meal.getStrMeal());

        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onClickListener((Meal) differ.getCurrentList().get(position));
        });
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder{
        FavoriteItemBinding binding;
        public FavoriteViewHolder(@NonNull FavoriteItemBinding favoriteItemBinding) {
            super(favoriteItemBinding.getRoot());
            this.binding = favoriteItemBinding;
        }
    }

    public interface OnFavoriteItemClickListener{
        void onClickListener(Meal meal);
    }
}
