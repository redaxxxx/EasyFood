package com.example.easyfood.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easyfood.data.pojo.Meal;
import com.example.easyfood.databinding.MealListItemBinding;

import java.util.Objects;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealsViewHolder> {

    public OnItemCLickListener onItemCLickListener;

    public MealsAdapter(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }
    DiffUtil.ItemCallback diffUtil = new DiffUtil.ItemCallback<Meal>(){

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
    public MealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MealsViewHolder(MealListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MealsViewHolder holder, int position) {
        Meal meal = (Meal) differ.getCurrentList().get(position);

        Glide.with(holder.itemView).load(meal.getStrMealThumb()).into(holder.binding.imgMeal);
        holder.binding.mealName.setText(meal.getStrMeal());

        holder.itemView.setOnClickListener(view -> {
            onItemCLickListener.onItemClick((Meal) differ.getCurrentList().get(position));
        });
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    class MealsViewHolder extends RecyclerView.ViewHolder{

        MealListItemBinding binding;
        public MealsViewHolder(MealListItemBinding mealListItemBinding) {
            super(mealListItemBinding.getRoot());

            binding = mealListItemBinding;
        }
    }

    public interface OnItemCLickListener{
        void onItemClick(Meal meal);
    }
}
